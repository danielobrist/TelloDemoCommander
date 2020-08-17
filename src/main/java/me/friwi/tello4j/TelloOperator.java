package me.friwi.tello4j;

import me.friwi.tello4j.api.drone.TelloDrone;
import me.friwi.tello4j.api.drone.WifiDroneFactory;
import me.friwi.tello4j.api.exception.TelloCommandTimedOutException;
import me.friwi.tello4j.api.exception.TelloConnectionTimedOutException;
import me.friwi.tello4j.api.exception.TelloCustomCommandException;
import me.friwi.tello4j.api.exception.TelloGeneralCommandException;
import me.friwi.tello4j.api.exception.TelloNetworkException;
import me.friwi.tello4j.api.exception.TelloNoValidIMUException;
import me.friwi.tello4j.api.video.TelloVideoExportType;
import me.friwi.tello4j.api.video.VideoWindow;
import me.friwi.tello4j.api.world.FlipDirection;
import me.friwi.tello4j.api.world.TurnDirection;

public class TelloOperator {

    public static void main(String args[]) {
        //Initialize a wifi drone
        try (TelloDrone drone = new WifiDroneFactory().build()) {
            drone.connect();
            System.out.print("connecting...");
            //Subscribe to state updates of our drone (e.g. current speed, attitude)
            drone.addStateListener((o, n) -> {
                //Do sth when switching from one to another state
            });
            //Create a video window to see things with our drones eyes
            //drone.addVideoListener(new VideoWindow());
            //...or use a custom video listener to process the single frames
            drone.addVideoListener(frame -> {
                //Do sth when we received a frame
            });
            //...[optional] select which type of frame you want to receive
            // a) [default] BUFFERED_IMAGE: Receive buffered images in each TelloVideoFrame
            // b) JAVACV_FRAME: Receive javacv frames in each TelloVideoFrame to further process them
            // c) BOTH: Receive both frame types in each TelloVideoFrame
            drone.setVideoExportType(TelloVideoExportType.BUFFERED_IMAGE);
            //...and tell the drone to turn on the stream
            /**
             * TODO: Streaming not yet fully working.
             */
            //drone.setStreaming(true);
            //Now perform a flight plan

            /**
             * The following commands are supported by the current iteration
             * of the TelloSimulator:
             *  .takeoff();
             *  .up(x);
             *  .down(x);
             *  .left(x);
             *  .right(x);
             *  .forward(x);
             *  .backward(x);
             *  .turn(Turndirection.RIGHT/LEFT , degrees);
             *  .flip(Flipdirection.FORWARD/BACKWARD/LEFT/RIGHT);
             *
             *  Commands are executed one after the other.
             */
            drone.takeoff();
            drone.turn(TurnDirection.RIGHT, 90);
            drone.forward(5000);
            drone.up(30);
            drone.backward(50);
            drone.flipp(FlipDirection.RIGHT);
            drone.down(20);
            drone.right(50);
            drone.turn(TurnDirection.RIGHT, 180);
            drone.left(100);
            drone.turn(TurnDirection.LEFT, 45);
            drone.right(50);
            drone.flip(FlipDirection.FORWARD);
            drone.turn(TurnDirection.LEFT, 25);
            drone.flip(FlipDirection.RIGHT);


            //drone.setStreaming(false);

            //drone.land();
            //Prevent our drone from being closed
            //(the drone is automatically closed when leaving the try-with-resource block)
            while (true) ;
        } catch (TelloNetworkException e) {
            if(e instanceof TelloConnectionTimedOutException){
                //The connection timed out because we did not send commands within the last 15 seconds.
                //The drone safely lands then.
                e.printStackTrace();
            }else {
                //Errors that occurred on the network side (e.g. parsing errors, connect error)
                //can be observed here
                e.printStackTrace();
            }
        } catch (TelloNoValidIMUException e) {
            //Commands that move the drone, apart from "takeoff", "land"
            //and "remote control" can fail due to no valid imu data.
            //This mainly happens when the ground under the drone does not
            //provide enough textual features for the drone to navigate properly.
            e.printStackTrace();
        } catch (TelloGeneralCommandException e) {
            //This exception is thrown when the drone reported an unspecified error
            //to the api. This can happen when the battery is too low for a
            //command to be executed
            e.printStackTrace();
        } catch (TelloCustomCommandException e) {
            //This exception is thrown when the drone reported an error with description
            //to the api. The reason can be obtained with e.getReason()
            e.printStackTrace();
        } catch (TelloCommandTimedOutException e) {
            //This exception is thrown when a command is not answered by the drone for 20 seconds
            e.printStackTrace();
        }
    }
}