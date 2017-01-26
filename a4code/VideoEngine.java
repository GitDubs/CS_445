//Ben Aston
//CS 445, Dr. Garrison
//Monday-Wednesday night lecture
//TA: Nannan Wen
package videoengine;

/**
 * This abstract data type is a predictive engine for video ratings in a streaming video system. It
 * stores a set of users, a set of videos, and a set of ratings that users have assigned to videos.
 *
 * @author Ben Aston
 */
public interface VideoEngine {

    /**
     * The abstract methods below are declared as void methods with no parameters. You need to
     * expand each declaration to specify a return type and parameters, as necessary. You also need
     * to include a detailed comment for each abstract method describing its effect, its return
     * value, any corner cases that the client may need to consider, any exceptions the method may
     * throw (including a description of the circumstances under which this will happen), and so on.
     * You should include enough details that a client could use this data structure without ever
     * being surprised or not knowing what will happen, even though they haven't read the
     * implementation.
     */

    /**
     * Adds a new video to the system.
     * 
     * @param toAdd video that is to be added to the system
     * @return boolean value true if the video has been added
     * false if the video already existed in the set and therefor no action was taken
     */
    public boolean addVideo(Video toAdd);

    /**
     * Removes an existing video from the system.
     * 
     * @return boolean value true if the video has been removed
     * false if the video did not exist in the set and therefor no action was taken
     * @param toRemove the video to be removed
     */
    public boolean removeVideo(Video toRemove);

    /**
     * Adds an existing television episode to an existing television series.
     * 
     * @param toAdd the TvEpisode to be added to the series
     * @param toAddTo the TvSeries to add the new episode to
     * @throws IllegalArgumentException if either toAdd or toAddTo do not exist in the system
     * @throws NullPointerException if either toAdd or toAddTo are null
     */
    public void addToSeries(TvEpisode toAdd, TvSeries toAddTo);

    /**
     * Removes a television episode from a television series.
     * Both the TvEpisode and TvSeries must already exist in the system, they will
     * not be added automatically
     * 
     * @param toRemove the episode to remove from the series
     * @param toRemoveFrom the series from which to remove the episode
     * @throws NullPointerException if toRemove or toRemoveFrom are null
     * @throws IllegalArgumentException if toRemove does not exist in toRemoveFrom
     * @throws IllegalArgumentException if toRemove does not exist in the system
     */
    public void removeFromSeries(TvEpisode toRemove, TvSeries toRemoveFrom);

    /**
     * Sets a user's rating for a video, as a number of stars from 1 to 5.
     * If the user already has a rating for toRate then that rating is updated
     * 
     * @param rating an int representing the user's rating of a video
     * @param toRate the video that the user is rating
     * @param user the user rating the video
     * @throws IllegalArgumentException if the number of stars is not in the accepted
     * range of 1 to 5
     * @throws IllegalArgumentException if the user or toRate are not in the system
     * @throws NullPointerException if user or toRate are null
     */
    public void rateVideo(User user, int rating, Video toRate);

    /**
     * Clears a user's rating on a video. If this user has rated this video and the rating has not
     * already been cleared, then the rating is cleared and the state will appear as if the rating
     * was never made. If this user has not rated this video, or if the rating has already been
     * cleared, then this method will throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared
     * @param theVideo video from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a rating on record for
     * the video
     * @throws NullPointerException if either the user or the video is null
     */
    public void clearRating(User theUser, Video theVideo);

    /**
     * Predicts the rating a user will assign to a video that they have not yet rated, as a number
     * of stars from 1 to 5.
     * Note that this method does not assign the rating it predicts as the user's rating
     * of video, but rather just returns a prediction
     * 
     * @param user the user the rating is being predicted for
     * @param video the video the user rating is being predicted for
     * @return an int representing the prediction for a rating that user would give to video
     * @throws NullPointerException if either the user or the video is null
     */
    public int predictRating(User user, Video video);

    /**
     * Suggests a video for a user (e.g.,based on their predicted ratings).
     * If the user has no ratings on record and no videos watched then the method
     * will return null
     * 
     * @return a Video that the system is suggesting to the user
     * @param user the user that the suggestion is being made for
     * @throws NullPointerException if the user is null
     * @throws IllegalArgumentException if the user is not in the system
     */
    public Video suggestVideo(User user);


}

