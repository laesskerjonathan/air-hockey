/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 ***/
package ch.laessker.jonathan.airhockey;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.invertM;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import android.widget.Toast;

import ch.laessker.jonathan.airhockey.game.Game;
import ch.laessker.jonathan.airhockey.objects.Mallet;
import ch.laessker.jonathan.airhockey.objects.Puck;
import ch.laessker.jonathan.airhockey.objects.Table;
import ch.laessker.jonathan.airhockey.programs.ColorShaderProgram;
import ch.laessker.jonathan.airhockey.programs.TextureShaderProgram;
import ch.laessker.jonathan.airhockey.util.DBHelper;
import ch.laessker.jonathan.airhockey.util.Geometry;
import ch.laessker.jonathan.airhockey.util.Geometry.Plane;
import ch.laessker.jonathan.airhockey.util.Geometry.Point;
import ch.laessker.jonathan.airhockey.util.Geometry.Ray;
import ch.laessker.jonathan.airhockey.util.Geometry.Sphere;
import ch.laessker.jonathan.airhockey.util.Geometry.Vector;
import ch.laessker.jonathan.airhockey.util.MatrixHelper;
import ch.laessker.jonathan.airhockey.util.SettingsValues;
import ch.laessker.jonathan.airhockey.util.TextureHelper;

public class AirHockeyRenderer implements Renderer {
    private final Context context;
    private final Activity activity;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] invertedViewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];

    private Table table;
    private Mallet malletP1;
    private Mallet malletP2;
    private Puck puck;
    private Game game;

    private int difficulty;
    private double difficultyFactor;

    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;

    private int texture;


    private int malletPressedP1 = -1;
    private int malletPressedP2 = -1;
    private Point P1MalletPosition;
    private Point P2MalletPosition;

    // wide goal
    private final float wideHalfGoal = 0.1f;



    // left and right limits
    private final float leftBound = -0.5f;
    private final float rightBound = 0.5f;
    // upper and bottom edges limits
    private final float farBound = -0.8f;
    private final float nearBound = 0.8f;

   /*
    private final float leftBound = -0.5f;
    private final float rightBound = 0.5f;
    private final float farBound = -0.9f;
    private final float nearBound = 0.9f;*/

    private Point previousP1MalletPosition;
    private Point previousP2MalletPosition;
    private int malletsActive = 0;

    private Point puckPosition;
    private Vector puckVector;

    public AirHockeyRenderer(Activity activity,Game game) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.game = game;
    }

    public void handleTouchPress(float normalizedX, float normalizedY, int pointerId) {
        Log.d("touchpress", Integer.toString(pointerId));
        Ray ray = convertNormalized2DPointToRay(normalizedX, normalizedY);

        // Now test if this ray intersects with the mallet by creating a
        // bounding sphere that wraps the mallet.
        Sphere malletBoundingSphereP1 = new Sphere(new Point(
                P1MalletPosition.x,
                P1MalletPosition.y,
                P1MalletPosition.z),
                malletP1.height / 2f);

        // Now test if this ray intersects with the mallet by creating a
        // bounding sphere that wraps the mallet.
        Sphere malletBoundingSphereP2 = new Sphere(new Point(
                P2MalletPosition.x,
                P2MalletPosition.y,
                P2MalletPosition.z),
                malletP2.height / 2f);

        // If the ray intersects (if the user touched a part of the screen that
        // intersects the mallet's bounding sphere), then set malletPressed =
        // true.
        Log.d("intersects1", Boolean.toString(Geometry.intersects(malletBoundingSphereP1, ray)));
        Log.d("intersects2", Boolean.toString(Geometry.intersects(malletBoundingSphereP2, ray)));

        if(Geometry.intersects(malletBoundingSphereP1, ray)) {
            malletPressedP1 = pointerId;
        }
        if(Geometry.intersects(malletBoundingSphereP2, ray)){
            malletPressedP2 = pointerId ;
        }
    }

    private Ray convertNormalized2DPointToRay(
            float normalizedX, float normalizedY) {
        // We'll convert these normalized device coordinates into world-space
        // coordinates. We'll pick a point on the near and far planes, and draw a
        // line between them. To do this transform, we need to first multiply by
        // the inverse matrix, and then we need to undo the perspective divide.
        final float[] nearPointNdc = {normalizedX, normalizedY, -1, 1};
        final float[] farPointNdc =  {normalizedX, normalizedY,  1, 1};

        final float[] nearPointWorld = new float[4];
        final float[] farPointWorld = new float[4];

        multiplyMV(
                nearPointWorld, 0, invertedViewProjectionMatrix, 0, nearPointNdc, 0);
        multiplyMV(
                farPointWorld, 0, invertedViewProjectionMatrix, 0, farPointNdc, 0);

        // Why are we dividing by W? We multiplied our vector by an inverse
        // matrix, so the W value that we end up is actually the *inverse* of
        // what the projection matrix would create. By dividing all 3 components
        // by W, we effectively undo the hardware perspective divide.
        divideByW(nearPointWorld);
        divideByW(farPointWorld);

        // We don't care about the W value anymore, because our points are now
        // in world coordinates.
        Point nearPointRay =
                new Point(nearPointWorld[0], nearPointWorld[1], nearPointWorld[2]);

        Point farPointRay =
                new Point(farPointWorld[0], farPointWorld[1], farPointWorld[2]);

        return new Ray(nearPointRay,
                Geometry.vectorBetween(nearPointRay, farPointRay));
    }

    private void divideByW(float[] vector) {
        vector[0] /= vector[3];
        vector[1] /= vector[3];
        vector[2] /= vector[3];
    }

    public void handleTouchUp(float normalizedX, float normalizedY, int pointerId){
        Ray ray = convertNormalized2DPointToRay(normalizedX, normalizedY);

        // Now test if this ray intersects with the mallet by creating a
        // bounding sphere that wraps the mallet.
        Sphere malletBoundingSphereP1 = new Sphere(new Point(
                P1MalletPosition.x,
                P1MalletPosition.y,
                P1MalletPosition.z),
                malletP1.height / 2f);

        // Now test if this ray intersects with the mallet by creating a
        // bounding sphere that wraps the mallet.
        Sphere malletBoundingSphereP2 = new Sphere(new Point(
                P2MalletPosition.x,
                P2MalletPosition.y,
                P2MalletPosition.z),
                malletP2.height / 2f);

        // If the ray intersects (if the user touched a part of the screen that
        // intersects the mallet's bounding sphere), then set malletPressed =
        // true.
        if(Geometry.intersects(malletBoundingSphereP1, ray))
            malletPressedP1 = -1;
        if(Geometry.intersects(malletBoundingSphereP2, ray))
            malletPressedP2 = -1;
    }


    public void handleTouchDrag(float normalizedX, float normalizedY, int pointerId) {

        if (malletPressedP1 == pointerId) {
            Log.d("malletPressedP1", Integer.toString(pointerId));
            Ray ray = convertNormalized2DPointToRay(normalizedX, normalizedY);
            // Define a plane representing our air hockey table.
            Plane plane = new Plane(new Point(0, 0, 0), new Vector(0, 1, 0));
            // Find out where the touched point intersects the plane
            // representing our table. We'll move the mallet along this plane.
            Point touchedPoint = Geometry.intersectionPoint(ray, plane);
            // Clamp to bounds                        

            previousP1MalletPosition = P1MalletPosition;

            // Clamp to bounds            
            P1MalletPosition = new Point(
                    clamp(touchedPoint.x,
                            leftBound + malletP1.radius,
                            rightBound - malletP1.radius),
                    malletP1.height / 2f,
                    clamp(touchedPoint.z,
                            0f + malletP1.radius,
                            nearBound - malletP1.radius)); // bottom half of the table

            // Now test if mallet has struck the puck.
            float distance =
                    Geometry.vectorBetween(P1MalletPosition, puckPosition).length();

            if (distance <= (puck.radius + malletP1.radius)) {
                // The mallet has struck the puck. Now send the puck flying
                // based on the mallet velocity.
                puckVector = Geometry.vectorBetween(
                        previousP1MalletPosition, P1MalletPosition);
                puckVector = puckVector.scale(0.9f);

                float distanceP1 =  Geometry.vectorBetween(P1MalletPosition, puckPosition.translate(puckVector)).length();

                if(distanceP1 <= (puck.radius + malletP1.radius)){
                    Log.d("Puckstuck", "puckstuck");
                    if(puckVector.length() != 0) {
                        puckPosition = puckPosition.translate(puckVector.scale(1 / puckVector.length() * 0.05f));
                    }
                }

            }
        }
        if(malletPressedP2 == pointerId)
        {
            Log.d("malletPressedP2", Integer.toString(pointerId));
            Ray ray = convertNormalized2DPointToRay(normalizedX, normalizedY);
            // Define a plane representing our air hockey table.
            Plane plane = new Plane(new Point(0, 0, 0), new Vector(0, 1, 0));
            // Find out where the touched point intersects the plane
            // representing our table. We'll move the mallet along this plane.
            Point touchedPoint = Geometry.intersectionPoint(ray, plane);
            // Clamp to bounds

            previousP2MalletPosition = P2MalletPosition;

            // Clamp to bounds
            P2MalletPosition = new Point(
                    clamp(touchedPoint.x,
                            leftBound + malletP2.radius,
                            rightBound - malletP2.radius),
                    malletP2.height / 2f,
                    clamp(touchedPoint.z,
                            farBound + malletP2.radius,
                            0f - malletP2.radius
                            )); // top half of the table

            // Now test if mallet has struck the puck.
            float distance =
                    Geometry.vectorBetween(P2MalletPosition, puckPosition).length();

            if (distance <= (puck.radius + malletP2.radius)) {
                // The mallet has struck the puck. Now send the puck flying
                // based on the mallet velocity.
                puckVector = Geometry.vectorBetween(
                        previousP2MalletPosition, P2MalletPosition);
                puckVector = puckVector.scale(0.9f);

                float distanceP2 =  Geometry.vectorBetween(P2MalletPosition, puckPosition.translate(puckVector)).length();

                if(distanceP2 <= (puck.radius + malletP2.radius)){
                    if(puckVector.length() != 0) {
                        puckPosition = puckPosition.translate(puckVector.scale(1 / puckVector.length() * 0.05f));
                    }
                }
            }
        }


    }


    private float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(value, min));
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        table = new Table();
        malletP1 = new Mallet(0.05f, 0.10f, 64);
        malletP2 = new Mallet(0.05f, 0.10f, 64);
        puck = new Puck(0.03f, 0.02f, 64);

        P1MalletPosition = new Point(0f, malletP1.height / 2f, 0.4f);
        P2MalletPosition = new Point(0f, malletP2.height / 2f, -0.4f);
        puckPosition = new Point(0f, puck.height / 2f, 0.25f);
        puckVector = new Vector(0f, 0f, 0f);
        difficulty = game.getDifficulty();
        this.setDifficultyFactor();

        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.gamefieldv4);

    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
                / (float) height, 1f, 10f);

        //setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
        setLookAtM(viewMatrix, 0, 0f, 2.2f, 0.01f, 0f, 0f, 0f, 0f, 1f, 0f); //working camera orientation
        //setLookAtM(viewMatrix, 0, 2f, 2.2f, 2f, 0f, 0f, 0f, 0f, 1f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);

        // Translate the puck by its vector
        puckPosition = puckPosition.translate(puckVector);

        float distanceP1 =
                Geometry.vectorBetween(P1MalletPosition, puckPosition).length();
        float distanceP2 =
                Geometry.vectorBetween(P2MalletPosition, puckPosition).length();

        // If the puck struck a side, reflect it off that side.
        if (puckPosition.x < leftBound + puck.radius
                || puckPosition.x > rightBound - puck.radius) {
            puckVector = new Vector(-puckVector.x, puckVector.y, puckVector.z);
            puckVector = puckVector.scale(0.9f);
           // puckVector = puckVector.scale(this.getDifficultyFactor());

        }
        if (puckPosition.z < farBound + puck.radius
                ) {
            // check if goal
                if (puckPosition.x < wideHalfGoal && puckPosition.x > -wideHalfGoal )
                {
                    //this is a goal for player 1
                    game.increaseScore(1,1);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Player 1: " + game.getScore(1) +
                                    "\nPlayer 2: " + game.getScore(2), Toast.LENGTH_SHORT).show();
                        }
                    });
                    game.checkWin(activity);
                }
            puckVector = new Vector(puckVector.x, puckVector.y, -puckVector.z);
            puckVector = puckVector.scale(0.9f);
            //puckVector = puckVector.scale(this.getDifficultyFactor());
        }

        if (puckPosition.z > nearBound - puck.radius) {
            // check if goal
            if (puckPosition.x < wideHalfGoal && puckPosition.x > -wideHalfGoal )
            {
                //this is a goal for player 2
                game.increaseScore(2, 1);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Player 1: " + game.getScore(1) +
                                "\nPlayer 2: " + game.getScore(2), Toast.LENGTH_SHORT).show();
                    }
                });
                game.checkWin(activity);
            }
            puckVector = new Vector(puckVector.x, puckVector.y, -puckVector.z);
            puckVector = puckVector.scale(0.9f);
           // puckVector = puckVector.scale(this.getDifficultyFactor());
        }




        if (distanceP2 <= (puck.radius + malletP2.radius)) {
            // The mallet has struck the puck. Now send the puck flying
            // based on the mallet velocity.
            puckVector = new Vector(-puckVector.x, -puckVector.y, -puckVector.z);
          //  puckVector = puckVector.scale(this.getDifficultyFactor());
        }

        if (distanceP1 <= (puck.radius + malletP2.radius)) {
            // The mallet has struck the puck. Now send the puck flying
            // based on the mallet velocity.
            puckVector = new Vector(-puckVector.x, -puckVector.y, -puckVector.z);
         //   puckVector = puckVector.scale(this.getDifficultyFactor());
        }




        // Clamp the puck position.
        puckPosition = new Point(
                clamp(puckPosition.x, leftBound + puck.radius, rightBound - puck.radius),
                puckPosition.y,
                clamp(puckPosition.z, farBound + puck.radius, nearBound - puck.radius)
        );

        // Friction factor
        puckVector = puckVector.scale(this.getDifficultyFactor());

        // Update the viewProjection matrix, and create an inverted matrix for
        // touch picking.
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0,
                viewMatrix, 0);
        invertM(invertedViewProjectionMatrix, 0, viewProjectionMatrix, 0);

        // Draw the table.
        positionTableInScene();
        textureProgram.useProgram();
        textureProgram.setUniforms(modelViewProjectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();

        // Draw the mallet 1
        positionObjectInScene(P1MalletPosition.x, P1MalletPosition.y,
                P1MalletPosition.z);
        colorProgram.useProgram();
        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        malletP1.bindData(colorProgram);
        malletP1.draw();

        // Draw the mallet 2
        positionObjectInScene(P2MalletPosition.x, P2MalletPosition.y,
                P2MalletPosition.z);
        colorProgram.useProgram();
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        malletP2.bindData(colorProgram);
        malletP2.draw();

        // Draw the puck.
        positionObjectInScene(puckPosition.x, puckPosition.y, puckPosition.z);
        colorProgram.setUniforms(modelViewProjectionMatrix, 0.5f, 0.3f, 0.6f);
        puck.bindData(colorProgram);
        puck.draw();
    }

    private void positionTableInScene() {
        // The table is defined in terms of X & Y coordinates, so we rotate it
        // 90 degrees to lie flat on the XZ plane.
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }

    // The mallets and the puck are positioned on the same plane as the table.
    private void positionObjectInScene(float x, float y, float z) {
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, x, y, z);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }
    private float getDifficultyFactor()
    {
        return (float) this.difficultyFactor;
    }


    private void setDifficultyFactor()
    {
        if (this.difficulty == 0){
            this.difficultyFactor = 0.995f;
        }
        else if (this.difficulty == 1){
            this.difficultyFactor = 1f;
        }
        else if (this.difficulty == 2)
        {
            this.difficultyFactor = 1.005f;
        }
        else{
            this.difficultyFactor = 1;
        }
    }
}