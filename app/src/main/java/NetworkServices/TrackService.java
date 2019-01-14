package NetworkServices;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TrackService{

    private RequestQueue queue;
    private NetworkResponseInterface rep;
    private Context con;

    public TrackService(Context con,NetworkResponseInterface rep) {
        this.rep = rep;
        this.con = con;
        this.queue = Volley.newRequestQueue(con);
    }

    public void PullStream(int id){
        StringRequest req = new StringRequest(Request.Method.GET, "http://www.squidswap.com:5698/user/1/shares", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    rep.OnResponse(new JSONObject(response));
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(req);
    }
}
