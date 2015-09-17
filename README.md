# play-blog  



**Feautures:**
* Write in markdown with realtime preview.
* Code highlight.
* Private and public posts
* Discuss comments 
* Embedded database (orientdb)
* Autosave while editing

Example: http://blogzella.tk

**Development usage:**  
 
 1. Edit conf/application.conf:  
 
 Add user:
 
      users = [
       {email: "dru", password: "123", name: "Вася"}
      ]
 
 Edit database url (Database will be created on startup)
 
      orient.db_url = "plocal:/home/dru/blog_db"

 2. Add disqus to post.scala.html
 
 Find `var disqus_shortname = 'your_discuss_id';` and specify your own from disquss admin.

 3. Launch `./activator run` from project folder
 
 4. Admin urls: `/login` `/logout` `/admin`
 
**Production usage:**   
 Production usage needs few steps more:
* Setup application secret;
* More see [play framework documentation](https://www.playframework.com/documentation/2.4.x/Home)

**FAQ**  

* How backup blog?

     Just copy database from your `orient.db_url`. Also you can manage database with OrientDB console. More see OrientDb docs

**Attention** 
* Users from `application.conf` updates on application restart - You can change password of exiting users or add new users; There is no multi user support yet. Users uses for authorization only. Also user's name displays in post author.
