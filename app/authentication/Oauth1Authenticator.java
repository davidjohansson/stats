package authentication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthRsaSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthSigner;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.AuthenticationException;

public class Oauth1Authenticator implements SpreadSheetAuthenticator {

    @Override
    public void authenticateService(SpreadsheetService service)
            throws AuthenticationException {
        try {
            authenticate(service);
        } catch (MalformedURLException e) {
            throw new AuthenticationException("Unable to authorize");
        } catch (OAuthException e) {
            throw new AuthenticationException("Unable to authorize");
        } catch (IOException e) {
            throw new AuthenticationException("Unable to authorize");
        }
    }

    private void authenticate(SpreadsheetService service) throws OAuthException,
            IOException, MalformedURLException {
        // //////////////////////////////////////////////////////////////////////////
        // STEP 1: Configure how to perform OAuth 1.0a
        // //////////////////////////////////////////////////////////////////////////

        boolean USE_RSA_SIGNING = false;

        // TODO: Update the following information with that obtained from
        // https://www.google.com/accounts/ManageDomains. After registering
        // your application, these will be provided for you.

        String CONSUMER_KEY = "alexanderljung.se";

        // TODO: Replace this with base-64 encoded private key conforming
        // to PKCS #8 standard, if and only if you will use RSA-SHA1
        // signing. Otherwise, this is the OAuth Consumer Secret retrieved
        // above. Be sure to store this value securely. Leaking this
        // value would enable others to act on behalf of your application!
        String CONSUMER_SECRET = "0hfppvuB6azfzPg1py_5aA2x";

        // Space separated list of scopes for which to request access.
        String SCOPES = "https://spreadsheets.google.com/feeds https://docs.google.com/feeds";

        // //////////////////////////////////////////////////////////////////////////
        // STEP 2: Set up the OAuth objects
        // //////////////////////////////////////////////////////////////////////////

        // You first need to initialize a few OAuth-related objects.
        // GoogleOAuthParameters holds all the parameters related to OAuth.
        // OAuthSigner is responsible for signing the OAuth base string.
        GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();

        // Set your OAuth Consumer Key (which you can register at
        // https://www.google.com/accounts/ManageDomains).
        oauthParameters.setOAuthConsumerKey(CONSUMER_KEY);

        // Initialize the OAuth Signer. If you are using RSA-SHA1, you must
        // provide
        // your private key as a Base-64 string conforming to the PKCS #8
        // standard.
        // Visit http://code.google.com/apis/gdata/authsub.html#Registered to
        // learn
        // more about creating a key/certificate pair. If you are using
        // HMAC-SHA1,
        // you must set your OAuth Consumer Secret, which can be obtained at
        // https://www.google.com/accounts/ManageDomains.
        OAuthSigner signer;
        if (USE_RSA_SIGNING) {
            signer = new OAuthRsaSha1Signer(CONSUMER_SECRET);
        } else {
            oauthParameters.setOAuthConsumerSecret(CONSUMER_SECRET);
            signer = new OAuthHmacSha1Signer();
        }

        // Finally, create a new GoogleOAuthHelperObject. This is the object you
        // will use for all OAuth-related interaction.
        GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(signer);

        // //////////////////////////////////////////////////////////////////////////
        // STEP 3: Get the Authorization URL
        // //////////////////////////////////////////////////////////////////////////

        // Set the scope for this particular service.
        oauthParameters.setScope(SCOPES);

        // This method also makes a request to get the unauthorized request
        // token,
        // and adds it to the oauthParameters object, along with the token
        // secret
        // (if it is present).
        oauthHelper.getUnauthorizedRequestToken(oauthParameters);

        // Get the authorization url. The user of your application must visit
        // this url in order to authorize with Google. If you are building a
        // browser-based application, you can redirect the user to the
        // authorization
        // url.
        String requestUrl = oauthHelper
                .createUserAuthorizationUrl(oauthParameters);
        System.out.println(requestUrl);
        System.out
                .println("Please visit the URL above to authorize your OAuth "
                        + "request token.  Once that is complete, press any key to "
                        + "continue...");
        System.in.read();

        // //////////////////////////////////////////////////////////////////////////
        // STEP 4: Get the Access Token
        // //////////////////////////////////////////////////////////////////////////

        // Once the user authorizes with Google, the request token can be
        // exchanged
        // for a long-lived access token. If you are building a browser-based
        // application, you should parse the incoming request token from the url
        // and
        // set it in GoogleOAuthParameters before calling getAccessToken().
        String token = oauthHelper.getAccessToken(oauthParameters);
        System.out.println("OAuth Access Token: " + token);
        System.out.println();

        // //////////////////////////////////////////////////////////////////////////
        // STEP 5: Make an OAuth authorized request to Google
        // //////////////////////////////////////////////////////////////////////////

        // Initialize the variables needed to make the request
        // URL feedUrl = new URL(variables.getFeedUrl());
        URL feedUrl = new URL("kolla upp raden ovan");
        System.out.println("Sending request to " + feedUrl.toString());
        System.out.println();

        // Set the OAuth credentials which were obtained from the step above.
        service.setOAuthCredentials(oauthParameters, signer);

        // Make the request to Google
        // See other portions of this guide for code to put here...

        // //////////////////////////////////////////////////////////////////////////
        // STEP 6: Revoke the OAuth token
        // //////////////////////////////////////////////////////////////////////////

        // System.out.println("Revoking OAuth Token...");
        // oauthHelper.revokeToken(oauthParameters);
        // System.out.println("OAuth Token revoked...");
    }
}
