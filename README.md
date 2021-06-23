# FETCH

## Table of Contents
1. [Overview](#Overview)
2. [User Stories](#User-Stories)
3. [Video Walkthrough](#Video-Walkthrough)
4. [Wireframes](#Wireframes)
5. [Notes](#Notes)
6. [Open-source libraries used](#Open-source-libraries-used)
7. [Schema](#Schema)

## Overview
### Description
   - The Application was created as part of Internship hiring process in Fetch Rewards. 
   - Android app created in Java that retrieves the data from provided JSON

### App Logo
<img src='https://github.com/Athma-Vaishali/Fetch/blob/master/fetch_icon.JPG' title='App Logo' />

## User Stories
Required features:
- [x] Display all the items grouped by "listId"
- [x] Sort the results first by "listId" then by "name" when displaying. (default)
- [x] Filter out any items where "name" is blank or null.

Additional features:
- [x] Added ExpandableListView for grouping by "listId"
- [x] Additional sort done: first by "listId" then by "ID" when selected
- [x] Added Menu Items to switch between sorts (name/ID)
- [x] Views are responsive for both landscape/portrait mode.

## Video Walkthrough

**Walkthrough 1**

<img src='https://github.com/Athma-Vaishali/Fetch/blob/master/walkthrough_1.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

**Walkthrough 2**

<img src='https://github.com/Athma-Vaishali/Fetch/blob/master/walkthrough_2.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

## Wireframes

### Digital Wireframes & Mockups
<img src="https://github.com/Athma-Vaishali/Fetch/blob/master/fetch_wireframe.JPG" height=500>

## Open-source libraries used

- [Android Async HTTP](https://github.com/codepath/CPAsyncHttpClient) - Simple asynchronous HTTP requests with JSON parsing

## Schema 
### Models
#### Item
Retrieved from provided Json

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | id      | Integer   | unique id for each item |
   | listId        | Integer | id used for grouping items |
   | name         | String     | name unique for each item |
   
### Networking
#### List of network requests by screen
   - Main Screen
        - (Read/GET) Retrieve items from provided Json            
   ```swift  
   AsyncHttpClient client = new AsyncHttpClient();
   client.get(URL, new JsonHttpResponseHandler() {
   @Override
   public void onSuccess(int i, Headers headers, JSON json) {
          Log.d(TAG,"onSuccess");
          JSONArray jsonArray=json.jsonArray;
          items.addAll(...));
   }      
   @Override
   public void onFailure(int i, Headers headers, String s, Throwable throwable) {
          Log.d(TAG,"onFailure",throwable);
   }
  });
