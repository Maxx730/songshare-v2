package NetworkServices;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

    private RequestQueue queue;
    private NetworkResponseInterface face;

    public LoginService(Context con,NetworkResponseInterface face){
        queue = Volley.newRequestQueue(con);
        this.face = face;
    }

    public void CheckLogin(final String username, final String password){
        StringRequest req = new StringRequest(Request.Method.POST, "http://www.squidswap.com:5698/user/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject rep = new JSONObject(response);
                    face.OnResponse(rep);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOGIN SERVICE ::: ","Error logging in...");
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };

        queue.add(req);
    }

    //Function used to sign the user up.
    public void SignUp(String username,String password,String repeat,String email){
        StringRequest req = new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        queue.add(req);
    }
}
