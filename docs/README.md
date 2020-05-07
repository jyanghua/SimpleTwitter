# Project 2 - SimpleTwitter

**Simple Twitter** is an android app that allows a user to view his Twitter timeline. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **30** hours spent in total

## User Stories

The following **required** functionality is completed:

- [x] User can **sign in to Twitter** using OAuth login
- [x]	User can **view tweets from their home timeline**
  - [x] User is displayed the username, name, and body for each tweet
  - [x] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
- [x] User can refresh tweets timeline by pulling down to refresh
- [x] User can **compose and post a new tweet**
  - [x] User can click a “Compose” icon in the Action Bar on the top right
  - [x] User can then enter a new tweet and post this to twitter
  - [x] User is taken back to home timeline with **new tweet visible** in timeline
  - [x] Newly created tweet should be manually inserted into the timeline and not rely on a full refresh
  - [x] User can **see a counter with total number of characters left for tweet** on compose tweet page


The following **optional** features are implemented:

- [x] User can view more tweets as they scroll with infinite pagination
- [x] User can tap a tweet to display a "detailed" view of that tweet
- [x] On the Twitter timeline, leverage the CoordinatorLayout to apply scrolling behavior that hides / shows the toolbar.
- [x] Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.org/android/Drawables#vector-drawables) where appropriate.
- [x] User can see embedded image media within the tweet detail view
- [ ] User sees an **indeterminate progress indicator** when any background or network task is happening
- [x] User can **see embedded image media within a tweet** on list or detail view.
- [x] User can **click a link within a tweet body** on tweet details view. The click will launch the web browser with relevant page opened.
- [ ] User can view following / followers list through any profile they view.
- [x] User is using **"Twitter branded" colors and styles**

- [ ] User can **select "reply" from detail view to respond to a tweet**
  - [ ] User that wrote the original tweet is **automatically "@" replied in compose**
- [x] User can move the "Compose" action to a FloatingActionButton instead of on the AppBar.
- [x] Compose tweet functionality is build using modal overlay
- [x] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.org/android/Using-Parceler).
- [ ] User can **open the twitter app offline and see last loaded tweets**. Persisted in SQLite tweets are refreshed on every application launch. While "live data" is displayed when app can get it from Twitter API, it is also saved for use in offline mode.
- [ ] When a user leaves the compose view without publishing and there is existing text, prompt to save or delete the draft. If saved, the draft should then be **persisted to disk** and can later be resumed from the compose view.
- [ ] User can enable app to **receive implicit intents** from other apps. When a link is shared from a web browser, it should pre-fill the text and title of the web page when composing a tweet.

The following **additional** features are implemented (or could be in the future):

- [x] Remove the media URL from the body since it is already previewed.
- [x] Replace the shortened Twitter URLs with the original shortened URLs to make it feel like the Twitter App.
  - [x] Using HTML to make shortened URLs work properly. Changed autoLink to setMovementMethod to stop overriding HTML body.
- [ ] Add a sign out button/ 3 dots menu on tool bar.
- [x] Disable Tweet button on dialog when EditText is empty (TextWatcher).
- [ ] Error message if there is no internet?
- [x] Retweet, Favorite/Like on the timeline and on the detailed view.
   - [x] Use different states for the favorite and retweet buttons.
- [ ] See the replies of the tweet on the detailed view.
- [ ] Display multiple images on the timeline and detailed view.
   - [ ] Click on image to expand to fullscreen view.
- [ ] Link mentions of other @users on the timeline and detailed view.



## Video Walkthrough

Here's a walkthrough of implemented user stories:

Part 2:

<img src='https://github.com/jyanghua/SimpleTwitter/blob/master/docs/gifs/SimpleTwitter_part2.gif' title='Video Walkthrough of Part 2' width='' alt='Video Walkthrough' />


Part 1:

<img src='https://github.com/jyanghua/SimpleTwitter/blob/master/docs/gifs/SimpleTwitter_part1.gif' title='Video Walkthrough of Part 1' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

- The biggest challenge of creating a Twitter client is the number of requirements that have to be completed to make it user friendly.
- The complexity of the API and the restrictions or number of API calls that can be made on this account are a bit limiting for what can be actually done.
- Programming the ToggleButton for favorites and retweets was not working properly due to the nature of the RecyclerView with the CoordinatorLayout, so a simple onClickListener was used instead of onChecked.


## Open-source libraries used

- [Android Async HTTP](https://github.com/codepath/CPAsyncHttpClient) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright 2020 Jack Yang Huang

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.