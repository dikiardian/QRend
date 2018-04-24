package com.example.dikiardian.qrend;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Diki on 4/24/2018.
 */

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String DOMAIN = "http://192.168.1.109";

    static String signInUser(String username, String password) {
        String API_URL =  DOMAIN + "/qrend/api/login.php";

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String JSONStringResult = null;

        try {
            Uri builtURI = Uri.parse(API_URL).buildUpon().build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();

            //request parameter
            String urlParameters = "username=" + username
                    + "&password=" + password;

            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
               /* Since it's JSON, adding a newline isn't necessary (it won't affect
                  parsing) but it does make debugging a *lot* easier if you print out the
                  completed buffer for debugging. */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            JSONStringResult = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, JSONStringResult);
        return JSONStringResult;
    }

    static String signOutUser(String username) {
        String API_URL =  DOMAIN + "/qrend/api/logout.php";

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String JSONStringResult = null;

        try {
            Uri builtURI = Uri.parse(API_URL).buildUpon().build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();

            //request parameter
            String urlParameters = "username=" + username;

            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
               /* Since it's JSON, adding a newline isn't necessary (it won't affect
                  parsing) but it does make debugging a *lot* easier if you print out the
                  completed buffer for debugging. */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            JSONStringResult = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, JSONStringResult);
        return JSONStringResult;
    }

    static String startEvent(int idUser, int idEvent, int isManual, int idDuration) {
        String API_URL =  DOMAIN + "/qrend/api/start_event.php";

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String JSONStringResult = null;

        try {
            Uri builtURI = Uri.parse(API_URL).buildUpon().build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();

            //request parameter
            String urlParameters = "id_user=" + idUser
                    + "&id_event=" + idEvent;

            if (isManual == 0) {
                urlParameters += "&duration=" + idDuration;
            }

            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
               /* Since it's JSON, adding a newline isn't necessary (it won't affect
                  parsing) but it does make debugging a *lot* easier if you print out the
                  completed buffer for debugging. */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            JSONStringResult = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, JSONStringResult);
        return JSONStringResult;
    }

    static String stopEvent(int idEvent) {
        String API_URL =  DOMAIN + "/qrend/api/stop_event.php";

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String JSONStringResult = null;

        try {
            Uri builtURI = Uri.parse(API_URL).buildUpon().build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();

            //request parameter
            String urlParameters = "id_event=" + idEvent;

            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
               /* Since it's JSON, adding a newline isn't necessary (it won't affect
                  parsing) but it does make debugging a *lot* easier if you print out the
                  completed buffer for debugging. */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            JSONStringResult = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, JSONStringResult);
        return JSONStringResult;
    }

    static String generateCode(String idUser, String idEvent, String code, String type) {
        String API_URL =  DOMAIN + "/qrend/api/generate_code.php";

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String JSONStringResult = null;

        try {
            Uri builtURI = Uri.parse(API_URL).buildUpon().build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();

            //request parameter
            String urlParameters = "id_user=" + idUser
                    + "&id_event=" + idEvent
                    + "&code=" + code
                    + "&type=" + type;

            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
               /* Since it's JSON, adding a newline isn't necessary (it won't affect
                  parsing) but it does make debugging a *lot* easier if you print out the
                  completed buffer for debugging. */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            JSONStringResult = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, JSONStringResult);
        return JSONStringResult;
    }

    static String getEventList(Integer userId) {
        String API_URL = DOMAIN + "/qrend/api/get_event_list.php?";
        String USER_ID_PARAM = "id_user";

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String JSONStringResult = null;

        try {
            Uri builtURI = Uri.parse(API_URL).buildUpon()
                    .appendQueryParameter(USER_ID_PARAM, userId.toString())
                    .build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
               /* Since it's JSON, adding a newline isn't necessary (it won't affect
                  parsing) but it does make debugging a *lot* easier if you print out the
                  completed buffer for debugging. */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            JSONStringResult = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, JSONStringResult);
        return JSONStringResult;
    }
}
