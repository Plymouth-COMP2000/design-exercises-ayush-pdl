package com.example.restaurant.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CourseworkApi {

    // From your API doc:
    // http://10.240.72.69/comp2000/coursework/  :contentReference[oaicite:7]{index=7}
    private static final String BASE_URL = "http://10.240.72.69/comp2000/coursework";

    // IMPORTANT: set your student_id ONCE here (keep consistent)
    // Example format in doc: student_123 :contentReference[oaicite:8]{index=8}
    public static final String STUDENT_ID = "10938330";

    private static RequestQueue queue;

    private static void initQueue(Context ctx) {
        if (queue == null) {
            queue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
    }

    // 1) Create student DB: POST /create_student/{student_id} :contentReference[oaicite:9]{index=9}
    public static void createStudentDb(Context ctx, ApiCallback<String> cb) {
        initQueue(ctx);
        String url = BASE_URL + "/create_student/" + STUDENT_ID;

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST, url, null,
                response -> cb.onSuccess(response.optString("message", "OK")),
                error -> cb.onError(error.getMessage() == null ? "Network/API error" : error.getMessage())
        );

        queue.add(req);
    }

    // 2) Create user: POST /create_user/{student_id} :contentReference[oaicite:10]{index=10}
    public static void createUser(Context ctx, UserModel user, ApiCallback<String> cb) {
        initQueue(ctx);
        String url = BASE_URL + "/create_user/" + STUDENT_ID;

        JSONObject body = new JSONObject();
        try {
            body.put("username", user.username);
            body.put("password", user.password);
            body.put("firstname", user.firstname);
            body.put("lastname", user.lastname);
            body.put("email", user.email);
            body.put("contact", user.contact);
            body.put("usertype", user.usertype);
        } catch (JSONException e) {
            cb.onError("Invalid JSON: " + e.getMessage());
            return;
        }

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST, url, body,
                response -> cb.onSuccess(response.optString("message", "User created")),
                error -> cb.onError(error.getMessage() == null ? "Network/API error" : error.getMessage())
        );

        queue.add(req);
    }

    // 3) Read specific user (used for login): GET /read_user/{student_id}/{username} :contentReference[oaicite:11]{index=11}
    public static void getUser(Context ctx, String username, ApiCallback<UserModel> cb) {
        initQueue(ctx);
        String url = BASE_URL + "/read_user/" + STUDENT_ID + "/" + username;

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject userObj = response.getJSONObject("user");

                        UserModel u = new UserModel();
                        u.username = userObj.optString("username", "");
                        u.password = userObj.optString("password", "");
                        u.firstname = userObj.optString("firstname", "");
                        u.lastname = userObj.optString("lastname", "");
                        u.email = userObj.optString("email", "");
                        u.contact = userObj.optString("contact", "");
                        u.usertype = userObj.optString("usertype", "");

                        cb.onSuccess(u);
                    } catch (Exception ex) {
                        cb.onError("Parse error: " + ex.getMessage());
                    }
                },
                error -> cb.onError(error.getMessage() == null ? "User not found / API error" : error.getMessage())
        );

        queue.add(req);
    }
}
