package NetworkServices;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserServices {

    private RequestQueue queue;
    private NetworkResponseInterface face;

    public UserServices(Context con,NetworkResponseInterface face){
        queue = Volley.newRequestQueue(con);
        this.face = face;
    }

    //Returns all the users from the database.
    public void GetUsers(){
        StringRequest req = new StringRequest(Request.Method.GET, "http://www.squidswap.com:5698/users", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    face.OnResponse(new JSONArray(response).getJSONObject(0));
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("USER SERVICE ::: ",error.toString());
            }
        });
        queue.add(req);
    }

    //Pulls a specific user based on the given id.
    public void GetUser(int id){
        StringRequest req = new StringRequest(Request.Method.GET, "http://www.squidswap.com:5698/user/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    face.OnResponse(new JSONArray(response).getJSONObject(0));
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("USER SERVICE ::: ",error.toString());
            }
        });
        queue.add(req);
    }

}
