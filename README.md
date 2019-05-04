# fundamentals_prep

A simple proof of concept Android app that makes use of some of the key technologies/methods presented in the [Codelabs for Android Developer Fundamentals (V2)](https://developer.android.com/courses/fundamentals-training/toc-v2)

The key concepts that this app makes use of are: 
* Unit Tests
* Espresso
* Cards
* AsyncTask (AsyncTaskLoader)
* Broadcast Receivers
* Notifications
* Shared Preferences
* Room 

The general outline of the app is as follows.  Users can post content to the database and scroll through content posted by other users.

A post contains at minimum a title, but can also include a description and post image.  Users can delete and edit posts that they have previously posted and other users can like posts (sticking only to 'likes' for now.  No commenting or downvoting, etc. as that is out of scope)

Users can change the look of their feed and their choice should be stored in the **Shared Preferences** so that users don't have to set the feed to their preferred style every time they open the app

A **Notification** should be sent to the user whenever they receive a 'like' on their post from another user

Any of our operations involving **Room** will make use of **AsyncTasks** and a post should always be encompassed into a single **Card** no matter which viewing style the users likes

Below are sketches of what the three different viewing styles should look like

![alt text](https://github.com/KernelFailure/fundamentals_prep/blob/master/ViewStyles.PNG)
