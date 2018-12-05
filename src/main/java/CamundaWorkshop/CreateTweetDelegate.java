package CamundaWorkshop;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import javax.inject.Named;
import java.io.File;
import java.util.Date;

@Named("createTweetDelegate")
public class CreateTweetDelegate implements JavaDelegate {
    private final Logger LOGGER = LoggerFactory.getLogger(CreateTweetDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String content = execution.getVariable("tweet").toString();
        String Initiator = execution.getVariable("Initiator").toString();
        content = content + "\nThis post was made by user: " + Initiator + " at " + new Date();
        StatusUpdate status = new StatusUpdate(content);

//        File file = new File("C:\\Users\\Pascal\\CamundaWorkshop\\Exercises\\Exercise1\\TwitterQaFromMaven\\src\\main\\resources\\camundaWorkshop.jpg");
//        status.setMedia(file);

        LOGGER.info("Publishing tweet: " + content);
        AccessToken accessToken = new AccessToken("220324559-jet1dkzhSOeDWdaclI48z5txJRFLCnLOK45qStvo", "B28Ze8VDucBdiE38aVQqTxOyPc7eHunxBVv7XgGim4say");
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("lRhS80iIXXQtm6LM03awjvrvk", "gabtxwW8lnSL9yQUNdzAfgBOgIMSRqh7MegQs79GlKVWF36qLS");
        twitter.setOAuthAccessToken(accessToken);
        twitter.updateStatus(status);
    }
}
