package com.example.yodreik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.*;
import android.util.Log;
import org.json.JSONObject;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import com.google.gson.Gson;

public class UserService {
    private static final String BASEPATH = "https://yodreik.ndbtea.tech/api";
    private static final String TAG = "UserService";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    public static JSONObject Create(String email, String password, String username) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<JSONObject> future = executor.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                String url = BASEPATH + "/auth/account";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("email", email);
                jsonInput.put("password", password);
                jsonInput.put("username", username);

                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                } catch (Exception e) {
                    throw e;
                }

                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return new JSONObject(response.toString());
                } else {
                    throw new Exception("Failed to create user, status: " + responseCode);
                }
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new Exception("Error during network operation: " + e.getMessage(), e);
        } finally {
            executor.shutdown();
        }
    }

    public static JSONObject Login(String login, String password) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<JSONObject> future = executor.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                String url = BASEPATH + "/auth/session";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("login", login);
                jsonInput.put("password", password);

                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                } catch (Exception e) {
                    throw e;
                }

                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return new JSONObject(response.toString());
                } else {
                    throw new Exception("Failed to login, status: " + responseCode);
                }
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new Exception("Error during network operation: " + e.getMessage(), e);
        } finally {
            executor.shutdown();
        }
    }

    public static JSONObject GetCurrentAccount(String accessToken) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<JSONObject> future = executor.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                String url = BASEPATH + "/account";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "Bearer " + accessToken);

                int responseCode = con.getResponseCode();

                Log.e("DREIK", "Status code: " + responseCode);
                Log.e("DREIK", "Request method: " + con.getRequestMethod());

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return new JSONObject(response.toString());
                } else {
                    throw new Exception("Failed to get current account, status: " + responseCode);
                }
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new Exception("Error during network operation: " + e.getMessage(), e);
        } finally {
            executor.shutdown();
        }
    }

    public static JSONObject CreateWorkout(String date, String duration, String kind, String accessToken) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<JSONObject> future = executor.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                String url = BASEPATH + "/workout";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "Bearer " + accessToken);
                con.setDoOutput(true);

                JSONObject jsonInput = new JSONObject();
                jsonInput.put("date", date);
                jsonInput.put("duration", Integer.parseInt(duration));
                jsonInput.put("kind", kind);

                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInput.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                } catch (Exception e) {
                    throw e;
                }

                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return new JSONObject(response.toString());
                } else {
                    throw new Exception("Failed to create workout, status: " + responseCode);
                }
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new Exception("Error during network operation: " + e.getMessage(), e);
        } finally {
            executor.shutdown();
        }
    }

    public static ActivityResponse GetActivity(String accessToken) throws Exception {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<ActivityResponse> future = executor.submit(new Callable<ActivityResponse>() {
        @Override
        public ActivityResponse call() throws Exception {
            String url = BASEPATH + "/activity";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return gson.fromJson(response.toString(), ActivityResponse.class);
            } else {
                throw new Exception("Failed to get activity, status: " + responseCode);
            }
        }
    });

    try {
        return future.get();
    } catch (InterruptedException | ExecutionException e) {
        throw new Exception("Error during network operation: " + e.getMessage(), e);
    } finally {
        executor.shutdown();
    }
}

    public static JSONObject GetStatistics(String accessToken) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<JSONObject> future = executor.submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                String url = BASEPATH + "/statistics";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "Bearer " + accessToken);

                int responseCode = con.getResponseCode();

                Log.e("DREIK", "Status code: " + responseCode);
                Log.e("DREIK", "Request method: " + con.getRequestMethod());

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return new JSONObject(response.toString());
                } else {
                    throw new Exception("Failed to get statistics, status: " + responseCode);
                }
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new Exception("Error during network operation: " + e.getMessage(), e);
        } finally {
            executor.shutdown();
        }
    }

    public static void DeleteWorkout(String accessToken, String id) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Void> future = executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                String url = BASEPATH + "/workout/" + id;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("DELETE");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "Bearer " + accessToken);

                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return null;
                } else {
                    throw new Exception("Failed to delete workout, status: " + responseCode);
                }
            }
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new Exception("Error during network operation: " + e.getMessage(), e);
        } finally {
            executor.shutdown();
        }
    }
}

