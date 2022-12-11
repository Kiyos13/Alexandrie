package com.example.alexandrie;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

class BookAPIRequest extends AsyncTask<String, Void, List<BookAPIRequestResult>> {

    protected Context context;
    public BookAPIRequest(Context context_) {
        context = context_;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Loading icon while the search is happening ?
        // No need I think ?
    }

    @Override
    protected List<BookAPIRequestResult> doInBackground(String... params) {

        // Fetch data from the API in the background.
        try {

            if(params.length < 1) return null; // Need a query

            URL requestUrl;
            HttpsURLConnection urlConnection = null;

            String requestUrlString = "https://api2.isbndb.com/books/" + params[0]; // Main query (book title/author/whatever)
            requestUrlString += "?page=1&pageSize=8"; // Params (we only want a few results)

            try {

                // Send the request
                requestUrl = new URL(requestUrlString);
                urlConnection = (HttpsURLConnection) requestUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("accept", "application/json");
                urlConnection.setRequestProperty("Authorization", "48762_d3f7deaf56a7a81db68f06d496be8991");

                int responseCode = urlConnection.getResponseCode();
                if(responseCode == 404) { // NOT FOUND
                    notifyNoBookFound();
                    return null;
                }

                if(responseCode != 200) { // OK
                    notifySomethingHappened(responseCode);
                    return null;
                }

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader responseBodyReader = new InputStreamReader(inputStream, "UTF-8");

                List<BookAPIRequestResult> foundBooks = null;
                try {
                    switch (params[1]) {
                        case "SEARCH":
                            foundBooks = parseRequestResults(responseBodyReader); break;
                        default: // ISBN Search
                            foundBooks = parseISBNRequestResults(responseBodyReader); break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return foundBooks; // Given to onPostExecute

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null) {
                    // Not yet disconnected, do it manually
                    urlConnection.disconnect();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<BookAPIRequestResult> bookList) {

        if (bookList == null) return;

        // Take only the first result
        BookAPIRequestResult book = null;
        if(bookList.size() < 1) {
            // Do not replace anything and show an error with the toaster
            notifyNoBookFound();
            return;
        } else {
            book = bookList.get(0);
        }

        // Replace every field in the DisplayDetailBook
        // TODO HERE MATHILDE
    }

    // JSON PARSING (ISBN search)
    private List<BookAPIRequestResult> parseISBNRequestResults (InputStreamReader inputStreamReader) throws IOException {
        JsonReader jsonReader = new JsonReader(inputStreamReader);
        List<BookAPIRequestResult> foundBooks = new ArrayList<BookAPIRequestResult>();

        String isbn = null, title = null, image = null, publishYear = null, publisher = null;
        List<String> authors = new ArrayList<String>();

        // 3rd level - book information
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {

            String key = jsonReader.nextName();
            switch(key) {
                case "isbn13": isbn = jsonReader.nextString(); break;
                case "title": title = jsonReader.nextString(); break;
                case "image": image = jsonReader.nextString(); break;
                case "date_published": publishYear = jsonReader.nextString(); break;
                case "publisher": publisher = jsonReader.nextString(); break;
                case "authors":
                    jsonReader.beginArray();
                    while(jsonReader.hasNext()) {
                        authors.add(jsonReader.nextString());
                    }
                    jsonReader.endArray();
                    break;

                default: jsonReader.skipValue(); break;
            }

        }
        jsonReader.endObject();

        foundBooks.add(new BookAPIRequestResult(isbn, title, authors, image, publishYear, publisher));

        jsonReader.close();
        return foundBooks;
    }

    // JSON PARSING (Book search)
    private List<BookAPIRequestResult> parseRequestResults(InputStreamReader inputStreamReader) throws IOException {

        JsonReader jsonReader = new JsonReader(inputStreamReader);
        List<BookAPIRequestResult> foundBooks = new ArrayList<BookAPIRequestResult>();

        // 1st level - body (books / total)
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {

            if(jsonReader.nextName().equals("books")) {

                // 2nd level - book aray
                jsonReader.beginArray();
                while(jsonReader.hasNext()) {

                    String isbn = null, title = null, image = null, publishYear = null, publisher = null;
                    List<String> authors = new ArrayList<String>();

                    // 3rd level - book information
                    jsonReader.beginObject();
                    while(jsonReader.hasNext()) {

                        String key = jsonReader.nextName();
                        switch(key) {
                            case "isbn13": isbn = jsonReader.nextString(); break;
                            case "title": title = jsonReader.nextString(); break;
                            case "image": image = jsonReader.nextString(); break;
                            case "date_published": publishYear = jsonReader.nextString(); break;
                            case "publisher": publisher = jsonReader.nextString(); break;
                            case "authors":
                                jsonReader.beginArray();
                                while(jsonReader.hasNext()) {
                                    authors.add(jsonReader.nextString());
                                }
                                jsonReader.endArray();
                                break;

                            default: jsonReader.skipValue(); break;
                        }

                    }
                    jsonReader.endObject();

                    foundBooks.add(new BookAPIRequestResult(isbn, title, authors, image, publishYear, publisher));

                }
                jsonReader.endArray();
                break;

            } else {
                jsonReader.skipValue(); // "total" key, not interesting
            }

        }

        jsonReader.close();
        return foundBooks;
    }

    private void notifyNoBookFound() {
        Toast.makeText(context, "No book found", Toast.LENGTH_SHORT).show();
    }

    private void notifySomethingHappened(int responseCode) {
        Toast.makeText(context, "Woops! Something Happened.\nResponse code : " + responseCode, Toast.LENGTH_LONG).show();
    }

}