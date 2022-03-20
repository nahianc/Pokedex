# Pokedex
Java based Android PokeDex application.
The app parses data from an online, externally maintained, JSON database. The Volley library was used to achieve this, as it provides 
quick and easy access to online resources. The data that is parsed is then stored locally within a object list, which will contain all 
pokemon within the database. Information stored within said database includes item like name, ID, description, stats, image urls, etc.
To display the list most effectively to the user, a RecyclerView was used to show all Pokemon contained within the list in a uniform 
display. To display images and the splash screen GIF, the Glide library was used as it provides quick and easy methods for developers
to grab image assets from URLs and plug them into views withing apps. 

![Screenshot_20220319_224322](https://user-images.githubusercontent.com/43014273/159145994-612dd250-f6a8-4b46-9fc9-e67dfe8cb0b1.png)

![Screenshot_20220319_224347](https://user-images.githubusercontent.com/43014273/159145995-6fcf2a14-f9ce-4d9c-9212-4bb663df1c65.png)

![Screenshot_20220319_224416](https://user-images.githubusercontent.com/43014273/159145996-6d5e8f94-2965-465b-ba52-28035552106a.png)

![Screenshot_20220319_224431](https://user-images.githubusercontent.com/43014273/159145997-f3c37bbf-8c5d-49fe-abf9-a938bce734e0.png)
