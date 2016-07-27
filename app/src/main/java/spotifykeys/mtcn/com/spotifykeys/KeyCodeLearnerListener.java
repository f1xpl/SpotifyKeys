package spotifykeys.mtcn.com.spotifykeys;

/**
 * Created by COMPUTER on 2016-07-26.
 */
public interface KeyCodeLearnerListener {
    void onSuccess();
    void onFailure();
    void onTimeout();
}
