package com.csc354cde335.kuselftour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class Information extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);



        // get the list view
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem)
                    expListView.collapseGroup(previousItem);
                previousItem = groupPosition;
            }
        });

       Intent intent = getIntent();
       String building = getIntent().getStringExtra("selected");

        // preparing list data
        prepareListData(building);

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


    }

    /*
     * Preparing the list data
     */
    private void prepareListData(String test) {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        if (KUSelfTourConstants.BEEKEY.equals(test)) {
            //Beekey
            listDataHeader.add("Beekey Info");
            listDataHeader.add("Clinical Experience & Outreach");
            listDataHeader.add("Elementary Education");
            listDataHeader.add("Secondary Education");
            listDataHeader.add("Special Education");
            // Adding child data
            List<String> beekeyInfo = new ArrayList<>();
            beekeyInfo.add("AKA: Education Center");
            beekeyInfo.add("Location: North Side");
            beekeyInfo.add("Description: Beekey Education Center is a busy place - providing a home for Kutztown's Elementary, Secondary and Special Education departments, the Office of Student Teaching and the Office of the Dean of the College of Education.");
            // Adding child data
            List<String> clinical = new ArrayList<>();
            clinical.add("Chair Name: Cathy Kirn");
            clinical.add("Phone: 610-683-4276");
            clinical.add("Office: Beekey Building Rm 222");
            clinical.add("Email: kreitz@kutztown.edu");
            // Adding child data
            List<String> elmEdu = new ArrayList<>();
            elmEdu.add("Chair Name: Jeanie Burnett");
            elmEdu.add("Phone: 610-683-4286");
            elmEdu.add("Office: Beekey Building Rm 201A");
            elmEdu.add("Email: burnett@kutztown.edu");
            // Adding child data
            List<String> secEdu = new ArrayList<>();
            secEdu.add("Chair Name: Theresa Stahler");
            secEdu.add("Phone: 610-683-4761");
            secEdu.add("Office: Beekey Building Rm 228");
            secEdu.add("Email: stahler@kutztown.edu");
            // Adding child data
            List<String> specEdu = new ArrayList<>();
            specEdu.add("Chair Name: Debra Lynch");
            specEdu.add("Phone: 610-683-4290");
            specEdu.add("Office: Beekey Building Rm 111");
            specEdu.add("Email: lynch@kutztown.edu");
            listDataChild.put(listDataHeader.get(0), beekeyInfo);
            listDataChild.put(listDataHeader.get(1), clinical);
            listDataChild.put(listDataHeader.get(2), elmEdu);
            listDataChild.put(listDataHeader.get(3), secEdu);
            listDataChild.put(listDataHeader.get(4), specEdu);
        }

        if (KUSelfTourConstants.BOEHM.equals(test)) {
            //Boehm
            listDataHeader.add("Boehm Info");
            listDataHeader.add("Biological Sciences");
            listDataHeader.add("Physical Sciences");
            // Adding child data
            List<String> boehmInfo = new ArrayList<>();
            boehmInfo.add("AKA: Science Center");
            boehmInfo.add("Location: North Side");
            boehmInfo.add("Description: Boehm Science Center is shared by the Biological and Physical Science departments. In addition to the shared facilities, each science department has its own specifically tailored facilities.");
            // Adding child data
            List<String> bio = new ArrayList<>();
            bio.add("Chair Name: Carol Mapes");
            bio.add("Phone: 610-683-4380");
            bio.add("Office: Boehm Building Rm 222");
            bio.add("Email: mapes@kutztown.edu");
            // Adding child data
            List<String> phySci = new ArrayList<>();
            phySci.add("Chair Name: Edward Simpson");
            phySci.add("Phone: 610-683-4445");
            phySci.add("Office: Boehm Building Rm 425");
            phySci.add("Email: simpson@kutztown.edu");
            listDataChild.put(listDataHeader.get(0), boehmInfo);
            listDataChild.put(listDataHeader.get(1), bio);
            listDataChild.put(listDataHeader.get(2), phySci);
        }
        if (KUSelfTourConstants.DEFRANCESCO.equals(test)) {
            //deFrancesco
            listDataHeader.add("deFrancesco Info");
            listDataHeader.add("Business Administration");
            listDataHeader.add("Modern Language Studies");
            // Adding child data
            List<String> deFranInfo = new ArrayList<>();
            deFranInfo.add("AKA: deFran");
            deFranInfo.add("Location: North Side");
            deFranInfo.add("Description: The deFrancesco Building is full of students and faculty from many disciplines.");
            //Adding child data
            List<String> business = new ArrayList<>();
            business.add("Chair Name: Roger Hibbs");
            business.add("Phone: 610-683-4580");
            business.add("Office: Defrancesco Building Rm 233A");
            business.add("Email: hibbs@kutztown.edu");
            // Adding child data
            List<String> mls = new ArrayList<>();
            mls.add("Chair Name: Christine Nunez");
            mls.add("Phone: 610-683-4428");
            mls.add("Office: DeFrancesco Building Rm 106B");
            mls.add("Email: nunez@kutztown.edu");
            listDataChild.put(listDataHeader.get(0), deFranInfo);
            listDataChild.put(listDataHeader.get(1), business);
            listDataChild.put(listDataHeader.get(2), mls);
        }

        if (KUSelfTourConstants.GRIM.equals(test)) {
            //Grim
            listDataHeader.add("Grim Info");
            // Adding child data
            List<String> grimInfo = new ArrayList<>();
            grimInfo.add("AKA: Science Building");
            grimInfo.add("Location: North Side");
            grimInfo.add("Description: Grim Science Building houses the university’s observatory and planetarium, where students learn about stars, constellations and the universe via the school’s astronomy program. Also, a computer lab for the Computer Science Department is located here alone with faculty officers for the Physical Sciences Department.");
            listDataChild.put(listDataHeader.get(0), grimInfo);
        }

        if (KUSelfTourConstants.LYTLE.equals(test)) {
            //Lytle
            listDataHeader.add("Lytle Info");
            listDataHeader.add("History");
            listDataHeader.add("Mathematics");
            listDataHeader.add("English");
            // Adding child data
            List<String> lytleInfo = new ArrayList<>();
            lytleInfo.add("AKA: Outskirts of Campus");
            lytleInfo.add("Location: North Side");
            lytleInfo.add("Description: Lytle Hall is the location of the university’s Departments of English, History and Mathematics. Lytle Hall is the farthest building from our dorms but it's a beautiful walk, and the back-and-forth of daily classes in Lytle is one sure way to help ward off the dreaded Freshman 15!");
            // Adding child data
            List<String> history = new ArrayList<>();
            history.add("Chair Name: Andrew Arnold");
            history.add("Phone: 610-683-4385");
            history.add("Office: Lytle Hall Rm 115");
            history.add("Email: arnold@kutztown.edu");
            // Adding child data
            List<String> math = new ArrayList<>();
            math.add("Chair Name: Paul Ache");
            math.add("Phone: 610-683-4410");
            math.add("Office: Lytle Hall Rm 227");
            math.add("Email: ache@kutztown.edu");
            // Adding child data
            List<String> eng = new ArrayList<>();
            eng.add("Chair Name: Andrew Vogel");
            eng.add("Phone: 610-683-4353");
            eng.add("Office: Lytle Hall Rm 132A");
            eng.add("Email: vogel@kutztown.edu");
            listDataChild.put(listDataHeader.get(0), lytleInfo);
            listDataChild.put(listDataHeader.get(1), history);
            listDataChild.put(listDataHeader.get(2), math);
            listDataChild.put(listDataHeader.get(3), eng);
        }


        if (KUSelfTourConstants.RICKENBACH.equals(test)) {
            //Rickenbach
            listDataHeader.add("Rickenbach Info");
            listDataHeader.add("Communication Studies");
            listDataHeader.add("Electronic Media");
            // Adding child data
            List<String> rickInfo = new ArrayList<>();
            rickInfo.add("AKA: Learning Center");
            rickInfo.add("Location: North Side");
            rickInfo.add("Description: Featuring a state-of-the-art Electronic Media Department complete with a working television studio, Rickenbach Learning Center may be one of the most exciting buildings on campus. Electronic Media students shoot complete television shows in this building!");
            // Adding child data
            List<String> eng1 = new ArrayList<>();
            eng1.add("Chair Name: Claire vanErns");
            eng1.add("Phone: 484-646-4396");
            eng1.add("Office: Learning Center Rm 218");
            eng1.add("Email: vanens@kutztown.edu");
            // Adding child data
            List<String> elect = new ArrayList<>();
            elect.add("Chair Name: Helen Bieber");
            elect.add("Phone: 610-683-4496");
            elect.add("Office: Learning Center Rm 209B");
            elect.add("Email: bieber@kutztown.edu");
            listDataChild.put(listDataHeader.get(0), rickInfo);
            listDataChild.put(listDataHeader.get(1), eng1);
            listDataChild.put(listDataHeader.get(2), elect);
        }

        if (KUSelfTourConstants.SHERIDAN.equals(test)) {
            //Sharadin
            listDataHeader.add("Sharadin Info");
            listDataHeader.add("Art Education & Crafts");
            listDataHeader.add("Communication Design");
            listDataHeader.add("Fine Arts");
            // Adding child data
            List<String> sharadinInfo = new ArrayList<>();
            sharadinInfo.add("AKA: Art Building");
            sharadinInfo.add("Location: North Side");
            sharadinInfo.add("Description: The Sharadin Art Building includes spaces for art teaching labs and studios. A main attraction is the Sharadin Art Gallery, where professionally executed solo and group exhibitions of contemporary art in a variant of mediums is presented.  An adjoining student gallery gives the opportunity to showcase work during students’ time at KU.");
            // Adding child data
            List<String> art = new ArrayList<>();
            art.add("Chair Name: John White");
            art.add("Phone: 610-683-4521");
            art.add("Office: Sharadin Art Studio Rm 401");
            art.add("Email: white@kutztown.edu");
            // Adding child data
            List<String> cd = new ArrayList<>();
            cd.add("Chair Name: Todd McFeely");
            cd.add("Phone: 610-683-4531");
            cd.add("Office: Sharadin Art Studio Rm 301");
            cd.add("Email: mcfeely@kutztown.edu");
            // Adding child data
            List<String> far = new ArrayList<>();
            far.add("Chair Name: Cheryl Hochberg");
            far.add("Phone: 610-683-4541");
            far.add("Office: Sharadin Art Studio Rm 117");
            far.add("Email: hochberg@kutztown.edu");
            listDataChild.put(listDataHeader.get(0), sharadinInfo);
            listDataChild.put(listDataHeader.get(1), art);
            listDataChild.put(listDataHeader.get(2), cd);
            listDataChild.put(listDataHeader.get(3), far);
        }

        if (KUSelfTourConstants.OLD_MAIN.equals(test)) {
            //Old Main
            listDataHeader.add("Old Main Info");
            listDataHeader.add("Sport Management & Leadership Studies");
            listDataHeader.add("Counseling & Student Affairs");
            listDataHeader.add("Music");
            listDataHeader.add("Anthropology & Sociology");
            listDataHeader.add("Computer Science & Information Technology");
            listDataHeader.add("Criminal Justice");
            listDataHeader.add("Philosophy");
            listDataHeader.add("Political Science");
            listDataHeader.add("Psychology");
            listDataHeader.add("Social Work");
            // Adding child data
            List<String> omInfo = new ArrayList<>();
            omInfo.add("AKA: Old Main");
            omInfo.add("Location: South Side");
            omInfo.add("Description: Built in 1866, Old Main is the oldest and most historic building on campus. In our founding years at the close of the nineteenth century, Old Main was our sole building, providing classroom, administrative and living space.");
            // Adding child data
            List<String> sport = new ArrayList<>();
            sport.add("Chair Name: Lorri Engstrom");
            sport.add("Phone: 610-683-4376");
            sport.add("Office: Old Main Rm 209");
            sport.add("Email: engstrom@kutztown.edu");
            // Adding child data
            List<String> cons = new ArrayList<>();
            cons.add("Chair Name: Margaret Herrick");
            cons.add("Phone: 610-683-4225");
            cons.add("Office: Old Main Rm 405");
            cons.add("Email: herrick@kutztown.edu");
            // Adding child data
            List<String> music = new ArrayList<>();
            music.add("Chair Name: Jeremy Justeson");
            music.add("Phone: 610-683-4551");
            music.add("Office: Old Main Rm 114");
            music.add("Email: justeson@kutztown.edu");
            // Adding child data
            List<String> soc = new ArrayList<>();
            soc.add("Chair Name: Joleen Greenwood");
            soc.add("Phone: 610-683-4248");
            soc.add("Office: Old Main Rm 465");
            soc.add("Email: greenwoo@kutztown.edu");
            // Adding child data
            List<String> comps = new ArrayList<>();
            comps.add("Chair Name: Lisa Frye");
            comps.add("Phone: 610-683-4422");
            comps.add("Office: Old Main Rm 254");
            comps.add("Email: frye@kutztown.edu");
            // Adding child data
            List<String> crj = new ArrayList<>();
            crj.add("Chair Name: Mahfuzul Khondaker");
            crj.add("Phone: 610-683-4886");
            crj.add("Office: Old Main Rm 364");
            crj.add("Email: khondake@kutztown.edu");
            // Adding child data
            List<String> phi = new ArrayList<>();
            phi.add("Chair Name: John Lizza");
            phi.add("Phone: 610-683-4322");
            phi.add("Office: Old Main Rm 313A");
            phi.add("Email: lizza@kutztown.edu");
            // Adding child data
            List<String> ps = new ArrayList<>();
            ps.add("Chair Name: Steve Lem");
            ps.add("Phone: 610-683-4471");
            ps.add("Office: Old Main Rm 310");
            ps.add("Email: lem@kutztown.edu");
            // Adding child data
            List<String> psy = new ArrayList<>();
            psy.add("Chair Name: Gergory Shelley");
            psy.add("Phone: 610-683-4456");
            psy.add("Office: Old Main Rm 384");
            psy.add("Email: shelley@kutztown.edu");
            // Adding child data
            List<String> sw = new ArrayList<>();
            sw.add("Chair Name: John Vafeas");
            sw.add("Phone: 610-683-4239");
            sw.add("Office: Old Main Rm 27");
            sw.add("Email: vafeas@kutztown.edu");
            listDataChild.put(listDataHeader.get(0), omInfo);
            listDataChild.put(listDataHeader.get(1), sport);
            listDataChild.put(listDataHeader.get(2), cons);
            listDataChild.put(listDataHeader.get(3), music);
            listDataChild.put(listDataHeader.get(4), soc);
            listDataChild.put(listDataHeader.get(5), comps);
            listDataChild.put(listDataHeader.get(6), crj);
            listDataChild.put(listDataHeader.get(7), phi);
            listDataChild.put(listDataHeader.get(8), ps);
            listDataChild.put(listDataHeader.get(9), psy);
            listDataChild.put(listDataHeader.get(10), sw);
        }

        if (KUSelfTourConstants.GRAD_CENTER.equals(test)) {
            //Grad Center
            listDataHeader.add("Graduate Center Info");
            listDataHeader.add("Geography");
            // Adding child data
            List<String> gcInfo = new ArrayList<>();
            gcInfo.add("AKA: Grad Center");
            gcInfo.add("Location: North Side");
            gcInfo.add("Description: Once the campus library, Kutztown University's Graduate Center houses the University’s Office of Assessment, as well as the dean of the College of Liberal Arts and Sciences, the Geography Department and classrooms.");
            // Adding child data
            List<String> geog = new ArrayList<>();
            geog.add("Chair Name: Richard Courtney");
            geog.add("Phone: 610-683-4364");
            geog.add("Office: Graduate Center Rm 104");
            geog.add("Email: courtney@kutztown.edu");
            listDataChild.put(listDataHeader.get(0), gcInfo);
            listDataChild.put(listDataHeader.get(1), geog);
        }
        if (KUSelfTourConstants.ROHRBACH.equals(test)) {
            //Rohrbach
            listDataHeader.add("Rohrbach Info");
            listDataHeader.add("Library Sciences & Instructional Technology");
            // Adding child data
            List<String> libInfo = new ArrayList<>();
            libInfo.add("AKA: Library");
            libInfo.add("Location: North Side");
            libInfo.add("Description: An array of useful resources is available to you in Kutztown University's Rohrbach Library. Throughout the building are computers to access internal and external resources. There are photocopiers, study rooms, study carrels, and ample seating areas.");
            // Adding child data
            List<String> lib = new ArrayList<>();
            lib.add("Chair Name: Andrea Harmer");
            lib.add("Phone: 610-683-4301");
            lib.add("Office: Rohrbach Library Rm 12");
            lib.add("Email: harmer@kutztown.edu");
            listDataChild.put(listDataHeader.get(0), libInfo);
            listDataChild.put(listDataHeader.get(1), lib);
        }

        if (KUSelfTourConstants.STUDENT_UNION_BUILDING.equals(test)) {
            //SUB
            listDataHeader.add("McFarland Student Union Building Info");
            //Adding child data
            List<String> subInfo = new ArrayList<>();
            subInfo.add("AKA: SUB");
            subInfo.add("Location: North Side");
            subInfo.add("Description: Some of the points of interest in the SUB include: Chick-fil-A, Starbucks, Burger Studio,\n" +
                    "Main Street Deli, Mexican on Main, Home on Main, Fresh 2 Go, and The Bear's Den.  Also located \n" +
                    "in the SUB is the University Bookstore, PSECU, a computer lab, a theater room, Alumni Auditorium, Fireside Lounge, \n" +
                    "Commuter's lounge, and so much more!");
            listDataChild.put(listDataHeader.get(0), subInfo);
        }

        if (KUSelfTourConstants.AF.equals(test)) {
            //AF
            listDataHeader.add("Academic Forum Info");
            //Adding child data
            List<String> afInfo = new ArrayList<>();
            afInfo.add("AKA: AF");
            afInfo.add("Location: North Side");
            afInfo.add("Description: The walls of the AF are lined with lecture halls, while the heart of\n" +
                    "the AF is the most central dining hall located on campus.  Starbucks is brewed at the\n" +
                    "coffee shop, along with Wrapz & Salad Garden, Italian Kitchen & the American Grill.");
            listDataChild.put(listDataHeader.get(0), afInfo);
        }

        if (KUSelfTourConstants.SCHAEFFER_AUDITORIUM.equals(test)) {
            //Auditorium
            listDataHeader.add("Schaeffer Auditorium Info");
            //Adding child data
            List<String> sInfo = new ArrayList<>();
            sInfo.add("AKA: Schaeffer");
            sInfo.add("Location: North Side");
            sInfo.add("Description: Our auditorium here on campus.");
            listDataChild.put(listDataHeader.get(0), sInfo);

        }

    }
}
