//James Kidd 20212056
package com.example.guessmaster;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.*;

import java.util.Random;


import android.view.View;
import android.content.DialogInterface;

import android.os.Bundle;

public class GuessMaster extends AppCompatActivity {
    private TextView entityName;
    private TextView ticketsum;
    private Button guessButton;
    private EditText userIn;
    private Button btnclearContent;
    private ImageView entityImage;
    String answer;

    private int numOfEntities;
    private Entity[] entities;
    private Entity entity;
    private int totalTicketNum;

    Politician jTrudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);
    Singer cDion = new Singer("Celine Dion", new Date("March", 30, 1961), "Female", "La voix du bon Dieu",
            new Date("November", 6, 1981), 0.5);
    Person myCreator = new Person("myCreator", new Date("September", 1, 2000), "Female", 1.0);

    Country usa = new Country("United States", new Date("July", 4, 1776), "Washington D.C.", 0.1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set the xml as the activity UI view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Specify the button in the view
        guessButton = (Button) findViewById(R.id.btnGuess);
        //Specify the button in the view
        btnclearContent = (Button) findViewById(R.id.btnClear);
        //EditText for user input
        userIn = (EditText) findViewById(R.id.guessinput);
        //TextView for total tickets
        ticketsum = (TextView) findViewById(R.id.ticket);
        //TextView for entity name
        entityName = (TextView) findViewById(R.id.entityName);
        //ImageView for entity image
        entityImage = (ImageView) findViewById(R.id.entityImage);

        new GuessMaster(); //create guessmaster class instance

        addEntity(jTrudeau);
        addEntity(cDion);
        addEntity(myCreator);
        addEntity(usa);
        //populate entities list

        changeEntity();
        welcomeToGame(entity);

        //On Click Listener action for clear button
        btnclearContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEntity();
            }
        });

        //On Click Listener action for submit button
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame(entity);
            }
        });

    }

    public GuessMaster() {
        numOfEntities = 0;
        entities = new Entity[10];
        totalTicketNum = 0;
    } //Constructor for GuessMaster

    public void addEntity(Entity entity) {
        entities[numOfEntities++] = entity.clone();
    }


    public void playGame(Entity entity) {
        answer = userIn.getText().toString();
        answer = answer.replace("\n", "").replace("\n", "");

        Date date = new Date(answer);

        if (date.precedes(entity.getBorn())) {
            AlertDialog.Builder incorrect = new AlertDialog.Builder(GuessMaster.this);
            incorrect.setTitle("Incorrect");
            incorrect.setMessage("Try a later date.");
            incorrect.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialop, int which) {
                    Toast.makeText(getBaseContext(), "Game.. is.. Starting.. Enjoy", Toast.LENGTH_SHORT).show();
                }
            });
            incorrect.show();
        }//User inputs the incorrect date (too early)
        else if (entity.getBorn().precedes(date)) {
            AlertDialog.Builder incorrect = new AlertDialog.Builder(GuessMaster.this);
            incorrect.setTitle("Incorrect");
            incorrect.setMessage("Try an earlier date.");
            incorrect.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialop, int which) {
                    Toast.makeText(getBaseContext(), "Game.. is.. Starting.. Enjoy", Toast.LENGTH_SHORT).show();
                }
            });
            incorrect.show();
        }//User inputs the incorrect date (too late)
        else {
            AlertDialog.Builder correct = new AlertDialog.Builder(GuessMaster.this);
            correct.setTitle("You won");
            correct.setMessage(entity.closingMessage());
            correct.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialop, int which) {
                    //Toast.makeText(getBaseContext(), entity.getAwardedTicketNumber(), Toast.LENGTH_SHORT).show();
                }
            });
            correct.show();
            totalTicketNum += entity.getAwardedTicketNumber();
            ticketsum.setText("Total Tickets: " + totalTicketNum);
            ContinueGame();
        }//User inputs the correct date
    }

    public int genRandomEntityId() {
        Random randomNumber = new Random();
        return randomNumber.nextInt(numOfEntities);
    }//return a random integer no larger than the number of enitities

    public void changeEntity() {
        int entityId = genRandomEntityId();
        entity = entities[entityId];
        entityName.setText(entity.getName());
        ImageSetter();
    }//change the entity being played (in a random process)

    public void ImageSetter() {
        switch ((String) entityName.getText()) {
            case "Justin Trudeau":
                entityImage.setImageResource(R.drawable.justint);
                break;
            case "Celine Dion":
                entityImage.setImageResource(R.drawable.celidion);
                break;
            case "myCreator":
                entityImage.setImageResource(R.drawable.carter);
                break;
            case "United States":
                entityImage.setImageResource(R.drawable.usaflag);
                break;
        }
    }//Set the entity Image to the correct jpg file

    public void welcomeToGame(Entity entity) {
        AlertDialog.Builder welcomealert = new AlertDialog.Builder(GuessMaster.this);
        welcomealert.setTitle("GuessMaster_Game_v3");
        welcomealert.setMessage(entity.welcomeMessage());
        welcomealert.setCancelable(false); //No Cancel Button

        welcomealert.setNegativeButton("START GAME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialop, int which) {
                Toast.makeText(getBaseContext(), "Game.. is.. Starting.. Enjoy", Toast.LENGTH_SHORT).show();
            }
        });
        //show dialog
        AlertDialog dialog = welcomealert.create();
        dialog.show();
    }//welcome dialog message

    public void ContinueGame() {
        if (!userIn.getText().toString().isEmpty()) {
            userIn.getText().clear();
        }
        changeEntity();
    }//continue the game


}