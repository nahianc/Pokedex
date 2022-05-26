# Pokedex
Java based Android PokeDex application.
I used Google's Firebase cloud DB for user authentication means. Users can register and sign in via email, or use their Facebook or Google
account to sign in.
The app parses data from an online, externally maintained, JSON database. The Volley library was used to achieve HTML URL connections, 
as it provides quick and easy access to online resources. The data that is parsed is then stored locally within a object ArrayList, 
which will contain all the Pokemon within the database. Information stored within said database includes item such as name, ID, 
description, stats, image urls, etc. 
To display the list most effectively to the user, a RecyclerView was used to show all Pokemon 
contained within the list in a uniform display. 
The thumbnails/images for the pokemon are not stored locally but instead the URL path is, 
so I used the bumptech Glide library for image loading to grab image assets from URLs and plug them into views within my app. 

![Screenshot_20220526_002848](https://user-images.githubusercontent.com/43014273/170416322-3998b6f6-3f1b-42c6-9e79-a212aa26c86a.png)

![Screenshot_20220526_002913](https://user-images.githubusercontent.com/43014273/170416323-cf49e064-c34c-4d15-b64b-7e37a77482bf.png)

![Screenshot_20220526_002931](https://user-images.githubusercontent.com/43014273/170416324-e74d63e0-317b-4940-a01b-1eadf8e053b1.png)

![Screenshot_20220319_224322](https://user-images.githubusercontent.com/43014273/159145994-612dd250-f6a8-4b46-9fc9-e67dfe8cb0b1.png)

![Screenshot_20220319_224347](https://user-images.githubusercontent.com/43014273/159145995-6fcf2a14-f9ce-4d9c-9212-4bb663df1c65.png)

![Screenshot_20220319_224416](https://user-images.githubusercontent.com/43014273/159145996-6d5e8f94-2965-465b-ba52-28035552106a.png)

![Screenshot_20220319_224431](https://user-images.githubusercontent.com/43014273/159145997-f3c37bbf-8c5d-49fe-abf9-a938bce734e0.png)
