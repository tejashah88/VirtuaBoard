package com.tejashah.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tejashah.ws.api.Point;
import com.tejashah.ws.api.Stroke;
import com.tejashah.ws.api.text.TextInput;
import com.tejashah.ws.api.text.TextOutput;

public class MyScriptCloud {
    protected static final String UTF_8 = "UTF-8";

    private String recognitionCloudURL;

    private List<Stroke> aggregator = new ArrayList<Stroke>();

    private final String applicationKey;

    private RecognitionListener listener;

    private final ObjectMapper mapper = new ObjectMapper();

    public MyScriptCloud(final String recognitionCloudURL, final String applicationKey) {
        this.recognitionCloudURL = recognitionCloudURL;
        this.applicationKey = applicationKey;
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected String getPostData(String textInput) {
        try {
            return URLEncoder.encode(textInput.toString(), UTF_8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    protected String getTextOutput(String json) {

        Reader jsonReader = new StringReader(json);
        TextOutput output = null;
        try {
            output = mapper.readValue(jsonReader, TextOutput.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final int selectedCandidateIdx = output.getResult().getTextSegmentResult().getSelectedCandidateIdx();
        return output.getResult().getTextSegmentResult().getCandidates().get(selectedCandidateIdx).getLabel();
    }

    protected HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded; charset=" + UTF_8);
        connection.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/28.0.1500.71 Chrome/28.0.1500.71 Safari/537.36");
        connection.setRequestMethod("POST");
        return connection;
    }

    public String recognize(Stroke[] strokes) throws IOException {

        final String input = getTextInput(strokes);

        HttpURLConnection connection = openConnection(new URL(recognitionCloudURL));
        OutputStream output = connection.getOutputStream();

        output.write(String.format("applicationKey=%s&textInput=", applicationKey, input).getBytes(UTF_8));

        System.out.println("Data posted : " + input);

        String postData = getPostData(input);
        output.write(postData.getBytes(UTF_8));
        output.flush();
        output.close();

        int responseCode = connection.getResponseCode();
        InputStream responseStream = (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) ? connection.getErrorStream() : connection.getInputStream();

        String json = URLDecoder.decode(readResponse(responseStream), UTF_8);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Response OK : " + json);
            return getTextOutput(json);
        } else {
            System.err.println("HTTP Error: " + responseCode + " " + connection.getResponseMessage());
            return null;
        }
    }

    private String getTextInput(Stroke[] strokes) {
        TextInput input = new TextInput();

        for (int strokeIndex = 0; strokeIndex < strokes.length; strokeIndex++) {
            input.addComponent();
            for (final Point point : strokes[strokeIndex].getPoints())
                input.addComponentPoint(point.x, point.y);
        }

        Writer jsonWriter = new StringWriter();
        try {
            mapper.writeValue(jsonWriter, input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonWriter.toString();
    }

    public static String readResponse(InputStream responseStream)
            throws IOException {
        StringBuffer response = new StringBuffer();
        byte[] data = new byte[2048];
        while (true) {
            int length = responseStream.read(data);
            if (length > 0) {
                response.append(new String(data, 0, length, UTF_8));
            } else {
                break;
            }
        }

        return response.toString();
    }
    
    @Deprecated
    //DO NOT USE THIS, WILL EAT UP REQUEST CALL QUOTA
    protected void addStroke(Stroke s) {
        aggregator.add(s);

        // TODO do it in a separate thread
        Stroke[] strokes = aggregator.toArray(new Stroke[aggregator.size()]);
        String recognized = null;
        try {
            recognized = recognize(strokes);
        } catch (IOException e) {
            // keep recognized null so that notifyListeners will notify an error
            // status
            recognized = null;
        } finally {
            if (listener != null) {
                listener.recognitionResult(recognized);
            }
        }
    }

    public void setListener(RecognitionListener listener) {
        this.listener = listener;
    }
}