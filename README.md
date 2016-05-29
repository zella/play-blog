# play-blog  



**Feautures:**
* Write in markdown with realtime preview.
* Code highlight.
* Private and public posts
* Discuss comments 
* Embedded database (orientdb)
* Autosave while editing
* Responsive design

Example: http://blogzella.tk

**Development usage:**  
 
 1. Edit `conf/application.conf`:  
 
 Add user:
 
      users = [
       {email: "dru", password: "123", name: "Вася"}
      ]
 
 Edit database url (Database will be created on startup)
 
      orient.db_url = "plocal:/home/dru/blog_db"

 Edit your disqus id 
 
      disqus.id = "your_disqus_id"
 

 2. Launch `./activator run` from project folder
 
 
 3. Go to browser `http://localhost:9000` 
 
 3. Admin urls: `/login` `/logout` `/admin`
 
**Production usage:**   

* Launch `./activator dist` and find distribution in `target/universal/play_blog_version.zip`
* More see [play framework documentation](https://www.playframework.com/documentation/2.4.x/Home)

**FAQ**  

* How backup blog?

     Just copy database files from your `orient.db_url` path. Also you can manage database with OrientDB console. More see OrientDb docs

**Attention** 
* Users from `application.conf` updates on application restart - You can change password of exiting users or add new users; There is no multi user support. Users uses for authorization only. Also user's name displays in post author.
