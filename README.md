# napptilus


For this app I have decided to implement MVVM architecture, this provide a best way
To structure the code in different parts. 


To make the development more easier I have used some libraries like: 


Dagger: It helps to automate dependency injection, a reason I used it is because once I configure it I do not have to write boilerplate code.

Retrofit: Allow us to make requests to an external API. I picked it up because it is a more friendly and reusable library to make requests rather than others like OkHttp.

Corountines: allow us to run asynchronous task avoiding that the UI get blocked, 
I used it because it is a much easier way to do background tasks, less code and it is a recommended solution for asynchronous programming on Android.

ViewModel: it is a business logic class and one of the benefits is to expose state to the UI using observable patterns and it also allows us to persist UI state when navigation through screen. When using MVVM you have to use this dependency by default.


Picasso/CircleImage: It helps us to load images. I used it because I have not used other libraries to load images. And I feel fine with it.  
	
RecyclerView: it helps us to create a list of views. I used it because it is the most modern way to create a list in android. Also, it is more customizable than others like ListView.
