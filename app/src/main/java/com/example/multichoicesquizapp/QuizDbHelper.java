package com.example.multichoicesquizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.multichoicesquizapp.QuizContract.*;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MultichoiceQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private  static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public  static  synchronized QuizDbHelper getInstance(Context context){
        if(instance == null){
            instance = new QuizDbHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";


        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, "+
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY (" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        //added
        final String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " +
                HistoryTable.TABLE_NAME + "( " +
                HistoryTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HistoryTable.COLUMN_SCORE + " TEXT, " +
                HistoryTable.COLUMN_DATE + " TEXT," +
                HistoryTable.COLUMN_PLAYER + " TEXT, " +
                HistoryTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                HistoryTable.COLUMN_DIFFICULTY + " TEXT,  " +
                HistoryTable.COLUMN_CATEGORY_NAME + " TEXT " +
                ")" ;

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_HISTORY_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HistoryTable.TABLE_NAME);
        onCreate(db);

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable(){

        Category c1 = new Category("Povijest");
        addCategory(c1);
        Category c2 = new Category("Zemljopis");
        addCategory(c2);
        Category c3 = new Category("Biologija");
        addCategory(c3);
        Category c4 = new Category("RAČUNALA");
        addCategory(c4);
        Category c5 = new Category("Matematika");
        addCategory(c5);
    }

    private  void addCategory (Category category){
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);

    }

    public void addHighscore(String score, String player, Integer category, String difficulty, String categoryName) {
        ContentValues cv = new ContentValues();
        cv.put(HistoryTable.COLUMN_SCORE,score);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        cv.put(HistoryTable.COLUMN_DATE, sdf.format(new Date()));
        cv.put(HistoryTable.COLUMN_PLAYER, player);
        cv.put(HistoryTable.COLUMN_CATEGORY_ID, category);
        cv.put(HistoryTable.COLUMN_DIFFICULTY, difficulty);
        cv.put(HistoryTable.COLUMN_CATEGORY_NAME, categoryName);
        db.insert(HistoryTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Koje je godine počeo Prvi Svjetski Rat?",
                "1914.", "1918.", "1941.", 1,
                Question.DIFFICULTY_MEDIUM, Category.POVIJEST );
        addQuestion(q1);
        Question q2 = new Question("Gdje se nekoć civilizacija Maja nalazila?",
                "Afrika", "Srednja Amerika", "Sjeverna Amerika", 2,
                Question.DIFFICULTY_MEDIUM, Category.POVIJEST );
        addQuestion(q2);
        Question q3 = new Question("Tijekom kojeg rata se odigrala Bitka u Ardenima?",
                "Vijetnamskog rata", "1.svjetskog rata", "2. svjettskog rata", 3,
                Question.DIFFICULTY_MEDIUM, Category.POVIJEST );
        addQuestion(q3);
        Question q4 = new Question("Koje godine su tenkovi prvi put korišteni u bitci?",
                "1945.", "1918.", "1916.", 3,
                Question.DIFFICULTY_MEDIUM, Category.POVIJEST );
        addQuestion(q4);
        Question q5 = new Question("Taj Mahal u Indiji je sagrađen u doba kojeg Carstva?",
                "Mogulsko Carstvo", "Novoperzijsko Carstvo", "Mongolsko Carstvo", 1,
                Question.DIFFICULTY_MEDIUM, Category.POVIJEST );
        addQuestion(q5);
        Question q6 = new Question("Ime prvog povjesničara bilo je?",
                "Herodot", "Historicus", "Gaj", 1,
                Question.DIFFICULTY_EASY, Category.POVIJEST );
        addQuestion(q6);
        Question q7 = new Question("Ime grčkog boga podzemnog svijeta je",
                "Apolon", "Had", "Ares", 2,
                Question.DIFFICULTY_EASY, Category.POVIJEST );
        addQuestion(q7);
        Question q8 = new Question("Kako su Grci računali godine?",
                "po godišnjim dobima", "po olimpijskim igrama", "ništa od navedenog", 2,
                Question.DIFFICULTY_EASY, Category.POVIJEST );
        addQuestion(q8);
        Question q9 = new Question("Koje je godine započeo Domovinski rat u Hrvatskoj?",
                "1991.", "1992.", "1993", 1,
                Question.DIFFICULTY_EASY, Category.POVIJEST );
        addQuestion(q9);
        Question q10 = new Question("Koji narod ne pripada skupini Južnih Slavena?",
                "Ukrajinci", "Slovenci", "Bugari", 1,
                Question.DIFFICULTY_EASY, Category.POVIJEST );
        addQuestion(q10);
        Question q11 = new Question("Koji od navedenih događaja je povezan s Bostonskom čajankom?",
                "Industrijska revolucija", "Američka revolucija", "Francuska revolucija", 2,
                Question.DIFFICULTY_HARD, Category.POVIJEST );
        addQuestion(q11);
        Question q12 = new Question("Kralj koje zemlje je objavio dokument zvani Magna Carta?",
                "Austrije", "Francuske", "Engleske", 3,
                Question.DIFFICULTY_HARD, Category.POVIJEST );
        addQuestion(q12);
        Question q13 = new Question("Koje godine je Brazil dobio neovisnost od Portugala?",
                "1822.", "1824.", "1834.", 1,
                Question.DIFFICULTY_HARD, Category.POVIJEST );
        addQuestion(q13);
        Question q14 = new Question("Koji od navedenih ljudi se smatra 'Ocem modernog organiziranog kriminala' u SAD-u?",
                "Carlo Gambino", "Al Capone", "Lucky Luciano", 3,
                Question.DIFFICULTY_HARD, Category.POVIJEST );
        addQuestion(q14);
        Question q15 = new Question("Koji povijesni događaj je doveo do izlaska sovjetskih trupa iz Čehoslovačke 1991. godine?",
                "Listopadska revolucija", "Baršunasta revolucija", "Praško proljeće", 2,
                Question.DIFFICULTY_HARD, Category.POVIJEST );
        addQuestion(q15);
        Question q16 = new Question("Koji je glavni grad Hrvatske?",
                "Split", "Osijek", "Zagreb", 3,
                Question.DIFFICULTY_EASY, Category.ZEMLJOPIS );
        addQuestion(q16);
        Question q17 = new Question("Vjetar koji je jak hladan i uglavnom suh zove se: ",
                "Jugo", "Maestral", "Bura", 3,
                Question.DIFFICULTY_EASY, Category.ZEMLJOPIS );
        addQuestion(q17);
        Question q18 = new Question("Koliko imamo ukupno kontinenata?",
                "5", "6", "7", 3,
                Question.DIFFICULTY_EASY, Category.ZEMLJOPIS );
        addQuestion(q18);
        Question q19 = new Question("Kojem kontinentu pripada Kanada?",
                "Sjevernoj Americi", "Južnoj Americi", "Europi", 1,
                Question.DIFFICULTY_EASY, Category.ZEMLJOPIS );
        addQuestion(q19);
        Question q20 = new Question("Koja je najviša planina na Zemlji?",
                "Elbrus", "Mount Everest", "Kilimanjaro", 2,
                Question.DIFFICULTY_EASY, Category.ZEMLJOPIS );
        addQuestion(q20);
        Question q21 = new Question("Kako se zove tip vegetacije gdje hladnija klima i kratka vegetacijska sezona sprječavaju rast drveća?",
                "Šikare", "Tajge", "Tundre", 3,
                Question.DIFFICULTY_MEDIUM, Category.ZEMLJOPIS );
        addQuestion(q21);
        Question q22 = new Question("Što dijeli zemlju na istok i zapad?",
                "Paralele", "Ekvator", "Nulti meridijan", 3,
                Question.DIFFICULTY_MEDIUM, Category.ZEMLJOPIS );
        addQuestion(q22);
        Question q23 = new Question("Koji je najveći grad na svijetu?",
                "Peking", "Mexico City", "New York", 2,
                Question.DIFFICULTY_MEDIUM, Category.ZEMLJOPIS );
        addQuestion(q23);
        Question q24 = new Question("Glavni grad Latvije je: ",
                "Riga", "Talin", "Kijev", 1,
                Question.DIFFICULTY_MEDIUM, Category.ZEMLJOPIS );
        addQuestion(q24);
        Question q25 = new Question("Koliko imamo meridijana?",
                "180", "220", "360", 3,
                Question.DIFFICULTY_MEDIUM, Category.ZEMLJOPIS );
        addQuestion(q25);
        Question q26 = new Question("Demografska tranzicija je: ",
                "Prijelaz iz visoke rodnosti i smrtnosti u nisku rodnost i smrtnost", "Prijelaz iz vioske smrtnosti u nisku smrtnost", "Prijelaz iz niske rodnosti u visoku rodnost", 1,
                Question.DIFFICULTY_HARD, Category.ZEMLJOPIS );
        addQuestion(q26);
        Question q27 = new Question("Najveća željeznička pruga na svijetu nalazi se u: ",
                "SAD", "Peruu", "Švicarska", 2,
                Question.DIFFICULTY_HARD, Category.ZEMLJOPIS );
        addQuestion(q27);
        Question q28 = new Question("Anđeoski vodopadi se nalaze u: ",
                "Venezueli", "Brazilu", "Boliviji", 1,
                Question.DIFFICULTY_HARD, Category.ZEMLJOPIS );
        addQuestion(q28);
        Question q29 = new Question("Salvador je poznat i kao : ",
                "Zemlja kave", "Vulkanska zemlja", "Zemlja vina", 2,
                Question.DIFFICULTY_HARD, Category.ZEMLJOPIS );
        addQuestion(q29);
        Question q30 = new Question("Najsuhlja pustinja na svijetu je: ",
                "Sahara", "Mohave", "Atacama", 3,
                Question.DIFFICULTY_HARD, Category.ZEMLJOPIS );
        addQuestion(q30);
        Question q31 = new Question("Što je biologija?",
                "Nauka koja se bavi proučavanjem tkiva.", "Nauka o životu i životnim procesima.", "Nauka koja proučava životne procese i funkcije biljnih organizama.", 2,
                Question.DIFFICULTY_EASY, Category.BIOLOGIJA );
        addQuestion(q31);
        Question q32 = new Question("Što je stanica?",
                "Osnovna gradivna i funkcionalna jedinica svih živih bića.", "Poklopac koji povezuje respiratorni i digestivni sustav.", "Osnovna morfološka i funkcionalna jedinica bubrega.", 1,
                Question.DIFFICULTY_EASY, Category.BIOLOGIJA );
        addQuestion(q32);
        Question q33 = new Question("Metabolizam je: ",
                "Promet materije i protok energije u organizmu.", "Cjelokupna živa komponenta stanice.", "Biokatalizator koji ubrzava kemijske reakcije u organizmu.", 1,
                Question.DIFFICULTY_EASY, Category.BIOLOGIJA );
        addQuestion(q33);
        Question q34 = new Question("Koji organi ulaze u sustav za potporu i kretanje?",
                "Mišići i kosti", "Tanko i debelo crijevo", "Ždrijelo i grkljan", 1,
                Question.DIFFICULTY_EASY, Category.BIOLOGIJA );
        addQuestion(q34);
        Question q35 = new Question("Prirodni neobnovljivi izvor je: ",
                "šuma", "ugljen", "zemljište", 2,
                Question.DIFFICULTY_EASY, Category.BIOLOGIJA );
        addQuestion(q35);
        Question q36 = new Question("Što je ekologija?",
                "Nauka o prirodi.", "Nauka koja proučava odnose između živih bića i okoliša.", "Isključivo biološka naučna disciplina.", 2,
                Question.DIFFICULTY_MEDIUM, Category.BIOLOGIJA );
        addQuestion(q36);
        Question q37 = new Question("Termičke dnevne razlike najizraženije su u: ",
                "vodenim ekosustavima", "kopnenim ekosustavima", "pustinjskim područjima", 3,
                Question.DIFFICULTY_MEDIUM, Category.BIOLOGIJA );
        addQuestion(q37);
        Question q38 = new Question("Populaciju čine sve jedinke: ",
                "sisavaca u šumi", "biljojeda u šumi", "vukova u šumi", 3,
                Question.DIFFICULTY_MEDIUM, Category.BIOLOGIJA );
        addQuestion(q38);
        Question q39 = new Question("Koje su krvne stanice?",
                "Eritrociti,leukociti i trombociti", "Organeli i citoplazma", "Eukarioti i prokarioti", 1,
                Question.DIFFICULTY_MEDIUM, Category.BIOLOGIJA );
        addQuestion(q39);
        Question q40 = new Question("Što od sljedećeg je dio mozga?",
                "Produžena moždina", "Adenohipofiza", "Neurohipofiza", 1,
                Question.DIFFICULTY_MEDIUM, Category.BIOLOGIJA );
        addQuestion(q40);
        Question q41 = new Question("Što je nefron?",
                "Osnovna morfološka i funkcionalna jedinica bubrega.", "Osnovna jedinica živčanog sustava. ", "Specijalne stanice vida.", 1,
                Question.DIFFICULTY_HARD, Category.BIOLOGIJA );
        addQuestion(q41);
        Question q42 = new Question("Što je slijepa mrlja?",
                "Spojnica između 2 živčane stanice.", "Stanica koja pod utjecajem svjetlosti dolazi u stanje nadraženosti.", "Dio u oku gdje nema fotoreceptora.", 3,
                Question.DIFFICULTY_HARD, Category.BIOLOGIJA );
        addQuestion(q42);
        Question q43 = new Question("Najveća koncentracija kisika je u: ",
                "bari", "potoku", "rijeci", 2,
                Question.DIFFICULTY_HARD, Category.BIOLOGIJA );
        addQuestion(q43);
        Question q44 = new Question("Nektonski organizam mora je: ",
                "bakalar", "rak samac", "crvena moruzgva", 1,
                Question.DIFFICULTY_HARD, Category.BIOLOGIJA );
        addQuestion(q44);
        Question q45 = new Question("Niska primarna produktivnost odlikuje: ",
                "morsku pučinu", "kišne šume", "plitke jezerske ekosustave", 1,
                Question.DIFFICULTY_HARD, Category.BIOLOGIJA );
        addQuestion(q45);
        Question q46 = new Question("Osnovna potvrdna tipka na računalu je: ",
                "esc", "tab", "enter", 3,
                Question.DIFFICULTY_EASY, Category.RAČUNALA);
        addQuestion(q46);
        Question q47 = new Question("Kratica za pokretanje Windows Explorera je 'windows tipka' + ",
                "e", "a", "o", 1,
                Question.DIFFICULTY_EASY, Category.RAČUNALA);
        addQuestion(q47);
        Question q48 = new Question("CTRL + V je kratica naredbe: ",
                "zalijepi (paste) ", "kopiraj (copy)", "umetni (insert)", 1,
                Question.DIFFICULTY_EASY, Category.RAČUNALA);
        addQuestion(q48);
        Question q49 = new Question("Kojom kraticom tipkovnice označavamo sve datoteke u mapi?",
                "CTRL + T", "ALT + M", "CTRL + A", 3,
                Question.DIFFICULTY_EASY, Category.RAČUNALA);
        addQuestion(q49);
        Question q50 = new Question("Upravitelj zadataka (task manager) pokreće se kraticom: ",
                "CTRL + ALT + U", "CTRL + ALT + DEL", "CTRL + ALT + T", 2,
                Question.DIFFICULTY_EASY, Category.RAČUNALA);
        addQuestion(q50);
        Question q51 = new Question("Kompjutorska kratica WAN odnosi se na: ",
                "World Area Network", "Wrong Area Network", "Wide Area Network", 3,
                Question.DIFFICULTY_MEDIUM, Category.RAČUNALA);
        addQuestion(q51);
        Question q52 = new Question("HTTP protokol ili ...",
                "Hi Protocol", "Hyper Text Transfer Protocol", "Hyper Transport", 2,
                Question.DIFFICULTY_MEDIUM, Category.RAČUNALA);
        addQuestion(q52);
        Question q53 = new Question("Kompjutorska kratica ISP odnosi se na: ",
                "Internet Service Provider", "Internet Protocol", "Internet System Protocol", 1,
                Question.DIFFICULTY_MEDIUM, Category.RAČUNALA);
        addQuestion(q53);
        Question q54 = new Question("Koji od navedenih programa radi s bazama podataka?",
                "Notepad", "MS Word", "MS Access", 3,
                Question.DIFFICULTY_MEDIUM, Category.RAČUNALA);
        addQuestion(q54);
        Question q55 = new Question("WordPad je program za: ",
                "obradu teksta", "obradu slike", "obradu zvuka", 1,
                Question.DIFFICULTY_MEDIUM, Category.RAČUNALA);
        addQuestion(q55);
        Question q56 = new Question("C# je objektno orijentirani programski jezik koji je razvila tvrtka: ",
                "Microsoft", "Apple", "Adobe Systems", 1,
                Question.DIFFICULTY_HARD, Category.RAČUNALA);
        addQuestion(q56);
        Question q57 = new Question("Ekstenzija 'tar' ukazuje da se u datoteci nalazi: ",
                "proračunska tablica", "arhiva", "video", 2,
                Question.DIFFICULTY_HARD, Category.RAČUNALA);
        addQuestion(q57);
        Question q58 = new Question("Koja zemlja koristi vršnu domenu 'bg'? ",
                "Bosna i Hercegovina", "Bugarska", "Bangladeš", 2,
                Question.DIFFICULTY_HARD, Category.RAČUNALA);
        addQuestion(q58);
        Question q59 = new Question("Tehnika za autentikaciju koja koristi jedinstvene fizičke karakteristike svakog čovjeka je: ",
                "vatrozid", "kriptografija", "biometrija", 3,
                Question.DIFFICULTY_HARD, Category.RAČUNALA);
        addQuestion(q59);
        Question q60 = new Question("SQL je strukturni jezik za rad sa: ",
                "bazama podataka", "desktop aplikacijama", "web stranicama", 1,
                Question.DIFFICULTY_HARD, Category.RAČUNALA);
        addQuestion(q60);
        Question q61 = new Question("Zbroj kutova u trokutu je: ",
                "120°", "180°", "90°", 2,
                Question.DIFFICULTY_EASY, Category.MATEMATIKA );
        addQuestion(q61);
        Question q62 = new Question("Kvocijent nasuprotne katete i hipotenuze pravokutnog trokuta naziva se ... ",
                "sinus kuta", "kosinus kuta", "tangens kuta", 1,
                Question.DIFFICULTY_EASY, Category.MATEMATIKA );
        addQuestion(q62);
        Question q63 = new Question("Prva nepoznanica u matematici označava se sa: ",
                "x", "y", "p", 1,
                Question.DIFFICULTY_EASY, Category.MATEMATIKA );
        addQuestion(q63);
        Question q64 = new Question("Linija koja spaja dva nesusjedna kuta mnogokuta naziva se: ",
                "visina", "simetrala", "dijagonala", 3,
                Question.DIFFICULTY_EASY, Category.MATEMATIKA );
        addQuestion(q64);
        Question q65 = new Question("Grana matematike koja se bavi analizom slučajnih pojava zove se: ",
                "Teorija kaosa", "Teorija vjerojatnosti", "Teorija relativnosti", 2,
                Question.DIFFICULTY_EASY, Category.MATEMATIKA );
        addQuestion(q65);
        Question q66 = new Question("Osnova matematičkog infinitezimalnog računa su integrali i ... ",
                "logaritmi", "derivacije", "interpolacije", 2,
                Question.DIFFICULTY_MEDIUM, Category.MATEMATIKA );
        addQuestion(q66);
        Question q67 = new Question("Statistički pojam koji određuje sredinu distribucije je?  ",
                "Klasa", "Nultočka", "Medijan", 3,
                Question.DIFFICULTY_MEDIUM, Category.MATEMATIKA );
        addQuestion(q67);
        Question q68 = new Question("Prema rimskom brojevnom sustavu 1000 se piše kao ...",
                "X", "M", "D", 2,
                Question.DIFFICULTY_MEDIUM, Category.MATEMATIKA );
        addQuestion(q68);
        Question q69 = new Question("Geometrijsko tijelo s dva  para paralelnih i sukladnih spurotnih stranica je: ",
                "pravokutnik", "paralelogram", "romb", 2,
                Question.DIFFICULTY_MEDIUM, Category.MATEMATIKA );
        addQuestion(q69);
        Question q70 = new Question("Dekadski broj 10 se u binarnom sustavu zapisuje kao: ",
                "1100", "1001", "1010", 3,
                Question.DIFFICULTY_MEDIUM, Category.MATEMATIKA );
        addQuestion(q70);
        Question q71 = new Question("Temeljna istina koja se ne dokazuje i služi kao osnova neke teorije zove se :  ",
                "poučak", "aksiom", "definicija", 2,
                Question.DIFFICULTY_HARD, Category.MATEMATIKA );
        addQuestion(q71);
        Question q72 = new Question("Kvadrilijun je broj koji se zapisuje sa koliko nula?  ",
                "48", "12", "24", 3,
                Question.DIFFICULTY_HARD, Category.MATEMATIKA );
        addQuestion(q72);
        Question q73 = new Question("Matematička konstanta koja definira odnos ospega i promjera kruga je: ",
                "Dudeneyev broj", "pi", "e", 2,
                Question.DIFFICULTY_HARD, Category.MATEMATIKA );
        addQuestion(q73);
        Question q74 = new Question("Eratostenovo sito je jednostavan algoritam za dobivanje svih...",
                "realnih brojeva", "racionalnih brojeva", "prostih brojeva", 3,
                Question.DIFFICULTY_HARD, Category.MATEMATIKA );
        addQuestion(q74);
        Question q75 = new Question("Dodekaedar je geometrijsko tijelo omeđeno s 12 ploha koje imaju oblik: ",
                "peterokuta", "četverokuta", "trokuta", 1,
                Question.DIFFICULTY_HARD, Category.MATEMATIKA );
        addQuestion(q75);


    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories(){
        List <Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if(c.moveToFirst()){
            do {
                Category category = new Category();
                category.setId((c.getInt(c.getColumnIndex(CategoriesTable._ID))));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public List<String> getAllScores() {
        List<String> scores = new ArrayList<String>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + HistoryTable.TABLE_NAME, null);

        if(c.moveToFirst())
        { do {
            //c.moveToFirst();
            scores.add(c.getString(c.getColumnIndex(HistoryTable.COLUMN_PLAYER)) + " -> Score " + c.getString(c.getColumnIndex(HistoryTable.COLUMN_SCORE)) + " : " + c.getString(c.getColumnIndex(HistoryTable.COLUMN_CATEGORY_NAME)) + " - " + c.getString(c.getColumnIndex(HistoryTable.COLUMN_DIFFICULTY)) + " ,  " + c.getString(c.getColumnIndex(HistoryTable.COLUMN_DATE)));
        }
            while (c.moveToNext());{
                //scores.add(c.getString(c.getColumnIndex(HistoryTable.COLUMN_PLAYER)) + " -> Score " + c.getString(c.getColumnIndex(HistoryTable.COLUMN_SCORE)) + " : " + c.getString(c.getColumnIndex(HistoryTable.COLUMN_CATEGORY_NAME)) + " - " + c.getString(c.getColumnIndex(HistoryTable.COLUMN_DIFFICULTY))+ " , " + c.getString(c.getColumnIndex(HistoryTable.COLUMN_DATE)));


            }
        }
        c.close();
        return scores;
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getColumnIndex(QuestionsTable._ID));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID((c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID))));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }


    public List<Question> getQuestions(int categoryID, String difficulty) {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};
        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null

        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getColumnIndex(QuestionsTable._ID));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID((c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID))));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
