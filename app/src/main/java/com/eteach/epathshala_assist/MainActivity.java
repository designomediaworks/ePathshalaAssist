package com.eteach.epathshala_assist;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.Service.GetVersionCode;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Utility.utilitys;
import com.eteach.epathshala_assist.Webservice.AttendanceWebservices;
import com.eteach.epathshala_assist.Webservice.EventWebservices;
import com.eteach.epathshala_assist.Webservice.ExamReportCardWebservices;
import com.eteach.epathshala_assist.Webservice.Fees_Webservices;
import com.eteach.epathshala_assist.Webservice.HomeworkWebservices;
import com.eteach.epathshala_assist.Webservice.LoginWebservice;
import com.eteach.epathshala_assist.Webservice.NotificationsWebservices;
import com.eteach.epathshala_assist.Webservice.SyllabusWebservices;
import com.eteach.epathshala_assist.Webservice.TimeTableWebservices;
import com.eteach.epathshala_assist.Webservice.ToppersWebservices;
import com.eteach.epathshala_assist.dataset.TBL_FEES;
import com.eteach.epathshala_assist.dataset.TBL_STUDENT;
import com.eteach.epathshala_assist.fragment.AttandanceFragment;
import com.eteach.epathshala_assist.fragment.ChangeAccountFragment;
import com.eteach.epathshala_assist.fragment.EventFragment;
import com.eteach.epathshala_assist.fragment.FeesFragment;
import com.eteach.epathshala_assist.fragment.HomescreenFragment;
import com.eteach.epathshala_assist.fragment.HomeworkImageViewFragment;
import com.eteach.epathshala_assist.fragment.eVotingFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.eteach.epathshala_assist.Utility.utilitys.builder;

public
class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    public static DrawerLayout drawer;
    private View navHeader;
    private Toolbar toolbar;
    public static FloatingActionButton fab;

    // index to identify current nav menu item
    public static int navItemIndex = 0;
    private static final String WELCOME_MESSAGE_KEY = "welcome_message";
    private static final String WELCOME_MESSAGE_CAPS_KEY = "welcome_message_caps";

    // tags used to attach the fragments
    private static final String TAG_eVoting = "eVoting";
    private static final String TAG_HOME = "Home";
    private static final String TAG_EVENTS = "Events";
    private static final String TAG_FEES = "Fees";
    private static final String TAG_NOTIFICATIONS = "Notifications";
    private static final String TAG_TRACKWAY = "Trackway";
    private static final String TAG_ASSIGNMENT = "Assignment";
    private static final String TAG_EXAMHISTORY = "Examhistory";
    private static final String TAG_ABOUTUS = "AboutUs";
    public static String CURRENT_TAG = TAG_HOME;
    private LoginWebservice loginWebservice;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    final Context context = this;
    DBHandler dbHandler = new DBHandler ( context );
    utilitys utilitys;
    Constants constants;
    ListView StudentListView;
    Dialog dialogChangeAccount;
    ArrayList <String> ListStudentID = new ArrayList <> ( );
    ArrayList <String> ListStudent = new ArrayList <> ( );
    List <TBL_STUDENT> tbl_StudentList = new ArrayList <> ( );
    private static CharSequence mSelectedAccount = "SelectAccount";
    private static String mSchoolName = "ePathshala";
    private int stdid, classid;
    private String mHouse;
    SweetAlertDialog pDialog;
    Snackbar snackbar;
    HashMap <String, String> user;
    private String FCM_token;
    private Boolean IsPendingFees = false;
    boolean isForceUpdate = true;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        toolbar = ( Toolbar ) findViewById ( R.id.toolbar );

        utilitys = new utilitys ( context );
        utilitys.checkLogin ( );
        user = utilitys.getUserDetails ( );
        constants = new Constants ( );
        setSupportActionBar ( toolbar );
        mHandler = new Handler ( );
        drawer = ( DrawerLayout ) findViewById ( R.id.drawer_layout );

        navigationView = ( NavigationView ) findViewById ( R.id.nav_view );
        Menu menu = navigationView.getMenu ();
        MenuItem menuItem = menu.findItem ( R.id.nav_app_version );
        String versioncode = String.valueOf ( Constants.versionCode );
        String version = " Version : "+ versioncode.substring ( 0,1 )+"."+versioncode.substring ( 1 ) ;
        menuItem.setTitle ( version);

        fab = ( FloatingActionButton ) findViewById ( R.id.fab );

        new GetVersionCode ( context, this ).execute ( );

        //
        FCM_token = FirebaseInstanceId.getInstance ( ).getToken ( );
        FirebaseMessaging.getInstance ( ).subscribeToTopic ( "global" );

        try {
            if ( Constants.isLogin == 0 ) {
                pDialog = new SweetAlertDialog ( MainActivity.this, SweetAlertDialog.SUCCESS_TYPE );
                pDialog.setTitleText ( "Login Successful!" )
                        .setContentText ( "ePathShala School Assist" )
                        .show ( );
                new Handler ( ).postDelayed ( new Runnable ( ) {
                    @Override
                    public
                    void run ( ) {
                        pDialog.dismissWithAnimation ( );
                    }
                }, 5000 );
            }
            if ( Constants.FCMStoredOnServer == 0 ) {
                new FCM_Registration ( ).execute ( );
            }
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
        // Navigation view header
        navHeader = navigationView.getHeaderView ( 0 );
        // load toolbar titles from string resources
        activityTitles = getResources ( ).getStringArray ( R.array.nav_item_activity_titles );

        fab.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public
            void onClick ( View view ) {

                if ( ! constants.isNetworkConnected ( getApplicationContext ( ) ) ) {
                    snackbar.make ( view, "Please connect to the internet", Snackbar.LENGTH_LONG )
                            .setAction ( "Action", null ).show ( );
                } else {
                    new DownloadEvents ( ).execute ( );
                }
            }
        } );

        // load nav menu header data
        loadNavHeader ( );
        // initializing navigation menu
        setUpNavigationView ( );
        if ( savedInstanceState == null ) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment ( );
        }
        FirebaseconfigINIT ( );

    }

    private
    void FirebaseconfigINIT ( ) {
        try {
            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance ( );
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder ( )
                    .setDeveloperModeEnabled ( BuildConfig.DEBUG )
                    .build ( );
            mFirebaseRemoteConfig.setConfigSettings ( configSettings );
            mFirebaseRemoteConfig.setDefaults ( R.xml.remote_config_defaults );
            fetcheVoting_status ( );
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
    }

    private
    void fetcheVoting_status ( ) {
        try {
            long cacheExpiration = 3600;
            if ( mFirebaseRemoteConfig.getInfo ( ).getConfigSettings ( ).isDeveloperModeEnabled ( ) ) {
                cacheExpiration = 0;
            }
            mFirebaseRemoteConfig.fetch ( cacheExpiration )
                    .addOnCompleteListener ( this, new OnCompleteListener <Void> ( ) {
                        @Override
                        public
                        void onComplete ( @NonNull Task <Void> task ) {
                            if ( task.isSuccessful ( ) ) {
                                mFirebaseRemoteConfig.activateFetched ( );
                                boolean status = mFirebaseRemoteConfig.getBoolean ( "evoting_running_status" );
                                if ( status ) {
                                   // Constants.EVOTING_VOTING_STARTED = true;
                                    //showeVotinginfoView ( );
                                } else {
                                    return;
                                }
                            } else {
                                //Toast.makeText ( MainActivity.this, "Fetch Failed",
                                //               Toast.LENGTH_SHORT ).show( );
                            }
                            // displayeVotingDailog ();
                        }
                    } );
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
    }

    private
    void displayeVotingDailog ( ) {
        try {
            String fcm_eVoting_message = mFirebaseRemoteConfig.getString ( "evoting_message" );
            final LayoutInflater inflater = ( LayoutInflater ) context
                    .getSystemService ( Context.LAYOUT_INFLATER_SERVICE );

            final View view = inflater.inflate ( R.layout.dialog_evoting, null );

            final TextView tv_eVoting_title = ( TextView ) view.findViewById ( R.id.txt_evoting_title );
            tv_eVoting_title.setText ( context.getString ( R.string.evoting_title ) );

            final TextView tv_eVoting_message = ( TextView ) view.findViewById ( R.id.txt_evoting_message );
            tv_eVoting_message.setText ( fcm_eVoting_message );

            final AlertDialog dialog = new AlertDialog.Builder ( context )
                    .setTitle ( "" )
                    .setView ( view )
                    .setPositiveButton ( R.string.evoting_yes, new DialogInterface.OnClickListener ( ) {
                        @Override
                        public
                        void onClick ( DialogInterface dialogInterface, int i ) {
                            eVotingFragment eVotingFragment = new eVotingFragment ( );
                            getSupportFragmentManager ( ).beginTransaction ( )
                                    .replace ( R.id.frame, eVotingFragment, "ePathshala eVoting" )
                                    .addToBackStack ( TAG_HOME )
                                    .commit ( );
                            setToolbarTitlefromfragment ( "ePathshala eVoting" );
                        }
                    } )
                    .setNegativeButton ( R.string.gpvch_button_negative, null )
                    .create ( );
            dialog.show ( );
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
    }

    public
    void showeVotinginfoView ( ) {
        try {
            runOnUiThread ( new Runnable ( ) {
                @Override
                public
                void run ( ) {
                    displayeVotingDailog ( );
                }
            } );
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
    }

    public
    void closedrower ( ) {
        getSupportActionBar ( ).setDisplayHomeAsUpEnabled ( true );
    }

    private
    void loadNavHeader ( ) {
        // name, website

        // showing dot next to notifications label
        navigationView.getMenu ( ).getItem ( 3 ).setActionView ( R.layout.menu_dot );
    }

    private
    void loadHomeFragment ( ) {
        try {
            // selecting appropriate nav menu item
            selectNavMenu ( );
            // set toolbar title
            setToolbarTitle ( );
            // if user select the current navigation menu again, don't do anything
            // just close the navigation drawer
            if ( getSupportFragmentManager ( ).findFragmentByTag ( CURRENT_TAG ) != null ) {
                drawer.closeDrawers ( );
                // show or hide the fab button
                toggleFab ( );
                return;
            }
            // Sometimes, when fragment has huge data, screen seems hanging
            // when switching between navigation menus
            // So using runnable, the fragment is loaded with cross fade effect
            // This effect can be seen in GMail app
            Runnable mPendingRunnable = new Runnable ( ) {
                @Override
                public
                void run ( ) {
                    // update the main content by replacing fragments
                    Fragment fragment = getHomeFragment ( );
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager ( ).beginTransaction ( );
                    fragmentTransaction.setCustomAnimations ( android.R.anim.fade_in,
                                                              android.R.anim.fade_out );
                    fragmentTransaction.replace ( R.id.frame, fragment, CURRENT_TAG );
                    fragmentTransaction.commitAllowingStateLoss ( );
                }
            };

            // If mPendingRunnable is not null, then add to the message queue
            if ( mPendingRunnable != null ) {
                mHandler.post ( mPendingRunnable );
            }

            // show or hide the fab button
            toggleFab ( );

            //Closing drawer on item click
            drawer.closeDrawers ( );

            // refresh toolbar menu
            invalidateOptionsMenu ( );
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
    }

    private
    Fragment getHomeFragment ( ) {

        switch ( navItemIndex ) {
            case 0:
                HomescreenFragment homescreenFragment = new HomescreenFragment ( );
                return homescreenFragment;
            case 1://Attandance chart
                AttandanceFragment attandanceFragment = new AttandanceFragment ( );
                return attandanceFragment;
            case 2://chenge Acccount prompt
                EventFragment eventFragment = new EventFragment ( );
                return eventFragment;
            case 3://LogOut user
                Toast.makeText ( getApplicationContext ( ), "Logout user", Toast.LENGTH_SHORT ).show ( );
            /*case 4:
                AttandanceFragment attandanceFragment = new AttandanceFragment ();
                return attandanceFragment;*/
            /*case 5:
                AssignmentFragment assignmentFragment = new AssignmentFragment ();
                return assignmentFragment;
            case 6:
                FeesFragment feesFragment = new FeesFragment ();
                return feesFragment;
            case 7:
                TrackWayFragment trackWayFragment = new TrackWayFragment ();
                return trackWayFragment;*/
            default:
                return new HomescreenFragment ( );
        }
    }

    private
    void setToolbarTitle ( ) {
        getSupportActionBar ( ).setTitle ( activityTitles[navItemIndex] );
    }

    public
    void setToolbarTitlefromfragment ( String _title ) {
        getSupportActionBar ( ).setTitle ( _title );
    }

    private
    void selectNavMenu ( ) {
        navigationView.getMenu ( ).getItem ( navItemIndex ).setChecked ( true );
    }

    private
    void setUpNavigationView ( ) {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener ( new NavigationView.OnNavigationItemSelectedListener ( ) {

            // This method will trigger on item Click of navigation menu
            @Override
            public
            boolean onNavigationItemSelected ( MenuItem menuItem ) {

                //Check to see which item was being clicked and perform appropriate action
                switch ( menuItem.getItemId ( ) ) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_add_acount:
                        navItemIndex = 2;
                        startActivity ( new Intent ( MainActivity.this, LoginActivity.class ) );
                        drawer.closeDrawers ( );
                        return true;
                    case R.id.nav_cahngeaccount:
                        navItemIndex = 3;
                        ChangeAccount ( );
                        break;
                    case R.id.nav_Logout:
                        navItemIndex = 4;
                        Logout ( );
                        break;
                    case R.id.nav_about_us:
                        startActivity ( new Intent ( MainActivity.this, AboutUsActivity.class ) );
                        drawer.closeDrawers ( );
                        return true;
                    case R.id.nav_privacy_policy:
                        startActivity ( new Intent ( MainActivity.this, PrivacyPolicyActivity.class ) );
                        drawer.closeDrawers ( );
                        return true;
                    default:
                        navItemIndex = 0;
                }
                //Checking if the item is in checked state or not, if not make it in checked state
                if ( menuItem.isChecked ( ) ) {
                    menuItem.setChecked ( false );
                } else {
                    menuItem.setChecked ( true );
                }
                menuItem.setChecked ( true );

                loadHomeFragment ( );

                return true;
            }
        } );


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle ( this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer ) {

            @Override
            public
            void onDrawerClosed ( View drawerView ) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed ( drawerView );
            }

            @Override
            public
            void onDrawerOpened ( View drawerView ) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened ( drawerView );
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener ( actionBarDrawerToggle );

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState ( );
    }

    private
    void Logout ( ) {
        utilitys utilitys = new utilitys ( context );
        utilitys.logoutUser ( );
        dbHandler.deleteAll ( );
        dbHandler.deleteAllEvents ( );
        dbHandler.deleteAllFees ( );
        dbHandler.deleteAllnotifications ( );
        dbHandler.deleteAllReportCards ( );
        dbHandler.deleteAlltopperss ( );
        dbHandler.deleteAllHomeworks ( );
        dbHandler.deleteAlltimetable();
    }

    private
    void ChangeAccount ( ) {
        if ( ListStudent.size ( ) == 0 ) {
            getStudentList ( );
        }
        //viadiolog ( );
        android.support.v4.app.Fragment fragment;
        fragment = new ChangeAccountFragment ();
        Constants.navItemIndex = 25;
        android.support.v4.app.FragmentTransaction fragmentTransaction = ((MainActivity) context ).getSupportFragmentManager ().beginTransaction ();
        fragmentTransaction.replace ( R.id.frame, fragment);
        fragmentTransaction.addToBackStack (null);
        fragmentTransaction.commit ();
    }

    private
    ArrayList <String> getStudentList ( ) {
        try {
            dbHandler = new DBHandler ( getApplicationContext ( ) );
            if ( ListStudent.size ( ) == 0 ) {
                List <TBL_STUDENT> tbl_studentList = dbHandler.getAllStudentData ( );
                for ( TBL_STUDENT tblStudent : tbl_studentList ) {
                    ListStudent.add ( tblStudent.get_StudentData ( ) );
                }
            }
            String j = ListStudent.get ( 0 );

            JSONArray jsonArray = new JSONArray ( j );
            for ( int i = 0 ; i < jsonArray.length ( ) ; i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject ( i );
                tbl_StudentList.add ( new TBL_STUDENT ( jsonObject.getString ( "V_AdmissionNo" ).toString ( ).toUpperCase ( ),
                                                        jsonObject.getString ( "V_S_FName" ).toString ( ).toUpperCase ( )
                                                                + " " + jsonObject.getString ( "V_S_MName" ).toString ( ).toUpperCase ( )
                                                                + " " + jsonObject.getString ( "V_S_LName" ).toString ( ).toUpperCase ( ) ) );
            }
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
        return ListStudent;
    }

    private
    void viadiolog ( ) {
        String StudentJson = "";
        setToolbarTitlefromfragment ( "Home" );
        try {
            if ( ListStudentID.size ( ) == 0 ) {
                for ( int i = 0 ; i < ListStudent.size ( ) ; i++ ) {
                    StudentJson = ListStudent.get ( i );
                    JSONArray jsonArray = new JSONArray ( StudentJson );
                    for ( int j = 0 ; j < jsonArray.length ( ) ; j++ ) {
                        JSONObject jsonObject = jsonArray.getJSONObject ( j );
                        String stdno = jsonObject.getString ( "V_AdmissionNo" ).toString ( ).toUpperCase ( );
                        String studentname = jsonObject.getString ( "V_S_FName" ).toString ( ).toUpperCase ( )
                                + " " + jsonObject.getString ( "V_S_MName" ).toString ( ).toUpperCase ( )
                                + " " + jsonObject.getString ( "V_S_LName" ).toString ( ).toUpperCase ( );
                        stdid = jsonObject.getInt ( "Pk_Student_M" );
                        classid = jsonObject.getInt ( "Fk_ClassId" );
                        mHouse = jsonObject.getString ( "V_housename" );
                        ListStudentID.add ( stdno + "\n" + studentname );
                    }
                }
            }
            dialogChangeAccount = new Dialog ( MainActivity.this );
           // dialogChangeAccount.setTitle ( "Change Account" );
            LayoutInflater layoutInflater = ( LayoutInflater ) this.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
            View view = layoutInflater.inflate ( R.layout.listaccounts, null, false );
            dialogChangeAccount.setContentView ( view );
            dialogChangeAccount.setCancelable ( true );
            StudentListView = ( ListView ) dialogChangeAccount.findViewById ( R.id.listaccounts );
            StudentListView.setChoiceMode ( ListView.CHOICE_MODE_SINGLE );
            StudentListView.setAdapter ( new ArrayAdapter <String> ( this, android.R.layout.simple_list_item_single_choice, ListStudentID ) );
            StudentListView.setOnItemClickListener ( new AdapterView.OnItemClickListener ( ) {
                @Override
                public
                void onItemClick ( AdapterView <?> parent, View view, final int position, long id ) {
                    Button btn_login = ( Button ) dialogChangeAccount.findViewById ( R.id.btn_changeAccount );
                    ( ( TextView ) view ).setTextColor ( MainActivity.this.getResources ( ).getColor ( R.color.Text_default ) );
                    btn_login.setOnClickListener ( new View.OnClickListener ( ) {
                        @Override
                        public
                        void onClick ( View v ) {
                            utilitys = new utilitys ( getApplicationContext ( ) );
                            utilitys.createLoginSession ( mSchoolName, mSelectedAccount.toString ( ), String.valueOf ( stdid ), String.valueOf ( classid ),mHouse );
                            HomescreenFragment homescreenFragment = new HomescreenFragment ( );
                            getSupportFragmentManager ( ).beginTransaction ( )
                                    .replace ( R.id.frame, homescreenFragment, TAG_HOME )
                                    .addToBackStack ( null )
                                    .commit ( );
                            setToolbarTitlefromfragment ( "Home" );
                            dialogChangeAccount.dismiss ( );
                        }
                    } );
                    //Toast.makeText ( getApplicationContext (),"Stdudent "+ListStudentID.get(position).substring(0,9),Toast.LENGTH_LONG ).show ();
                    //mSelectedAccount =  ((TextView ) view).getText () ;
                    Constants.ADMISSION_NO = ListStudentID.get ( position ).substring ( 0, 9 );
                    Constants.STUDENT_CLASS_ID = String.valueOf ( classid );
                    mSelectedAccount = ListStudentID.get ( position ).substring ( 0, 9 );
                    mSchoolName = "ePathshala";
                }
            } );

            dialogChangeAccount.show ( );
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
    }

    public
    void onBackPressed ( ) {
        if ( drawer.isDrawerOpen ( GravityCompat.START ) ) {
            drawer.closeDrawers ( );
            return;
        }
        try {
            // This code loads home fragment when back key is pressed
            // when user is in other fragment than home
            if ( shouldLoadHomeFragOnBackPress ) {
                // checking if user is on other navigation menu
                // rather than home
/*            if (navItemIndex != 0) {
                navItemIndex = 0;
                Constants.navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }*/
                if ( getSupportFragmentManager ( ).getBackStackEntryCount ( ) > 0 ) {
                    getSupportFragmentManager ( ).popBackStack ( );
                }

                if ( Constants.navItemIndex == 21 || Constants.navItemIndex == 22  )
                {
                    Constants.navItemIndex = 23;
                    CURRENT_TAG = TAG_eVoting;
                    loadHomeFragment ( );
                    getSupportActionBar ( ).setDisplayShowTitleEnabled ( true );
                }
                else if ( Constants.navItemIndex != 0 ) {
                    navItemIndex = 0;
                    Constants.navItemIndex = 0;
                    CURRENT_TAG = TAG_HOME;
                    loadHomeFragment ( );
                    getSupportActionBar ( ).setDisplayShowTitleEnabled ( true );
                }
                else if ( Constants.navItemIndex == 0 ) {
                    //dialogquictapp();
                    finishAffinity ( );
                    super.onBackPressed ( );
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
    }

    public
    Boolean CheckUserIsBlocked ( ) {
        if ( IsPendingFees == true )
            pDialog = new SweetAlertDialog ( MainActivity.this, SweetAlertDialog.WARNING_TYPE );
        pDialog.setTitleText ( "Term Fees Pending" )
                .setContentText ( "ePathShala School Assist" )
                .setConfirmClickListener ( new SweetAlertDialog.OnSweetClickListener ( ) {
                    @Override
                    public
                    void onClick ( SweetAlertDialog sweetAlertDialog ) {
                        Constants.navItemIndex = 10;
                        FeesFragment feesFragment = new FeesFragment ( );
                        getSupportFragmentManager ( ).beginTransaction ( )
                                .replace ( R.id.frame, feesFragment, TAG_FEES )
                                .addToBackStack ( null )
                                .commit ( );
                        setToolbarTitlefromfragment ( TAG_FEES );
                    }
                } )
                .setCancelClickListener ( new SweetAlertDialog.OnSweetClickListener ( ) {
                    @Override
                    public
                    void onClick ( SweetAlertDialog sweetAlertDialog ) {
                        finishAffinity ( );
                    }
                } )
                .show ( );
        return false;
    }

    private
    String getFeespaidstatus ( ) {
        List <TBL_FEES> feesList = new ArrayList <> ( );
        TBL_FEES tbl_fees = new TBL_FEES ( );
        String PaidStatus = "";
        DateFormat dateFormat = new SimpleDateFormat ( "ddMMyyyy" );
        Date date = Calendar.getInstance ( ).getTime ( );
        dbHandler = new DBHandler ( getApplicationContext ( ) );
        utilitys = new utilitys ( getApplicationContext ( ) );
        String j = "";
        try {
            HashMap <String, String> user = utilitys.getUserDetails ( );
            Cursor tbl_feesList = dbHandler.getStudentFees ( user.get ( utilitys.KEY_STD_NO ) );
            j = tbl_feesList.getString ( 0 );
            JSONArray jsonArray = new JSONArray ( j );
            for ( int i = 0 ; i < jsonArray.length ( ) ; i++ ) {
                JSONObject jsonObject = jsonArray.getJSONObject ( i );
                feesList.add ( new TBL_FEES ( jsonObject.getString ( "Frequency" ).toString ( ),
                                              jsonObject.getString ( "FeesCategory" ).toString ( ),
                                              jsonObject.getString ( "FeeSubCategory" ).toString ( ),
                                              jsonObject.getString ( "Period" ).toString ( ),
                                              jsonObject.getString ( "V_PaymentMode" ).toString ( ),
                                              jsonObject.getString ( "ActualAmount" ).toString ( ),
                                              jsonObject.getString ( "DiscountAmt" ).toString ( ),
                                              jsonObject.getString ( "PaymentAmount" ).toString ( ),
                                              jsonObject.getString ( "Balance" ).toString ( ),
                                              jsonObject.getString ( "Status" ).toString ( ),
                                              jsonObject.getString ( "PaymentDate" ).toString ( ),
                                              jsonObject.getString ( "CustomerID" ).toString ( ) ) );
            }
            String Term = feesList.get ( 0 ).get_FeesCategory ( );
            String d1 = feesList.get ( 0 ).get_Feesperiod ( );
            String d2 = feesList.get ( 0 ).get_Feesperiod ( );
            String d1sub = d1.substring ( 0, 10 ).replaceAll ( "/", "" );
            String d2sub = d1.substring ( 14, 24 ).replaceAll ( "/", "" );
            Date StartDate = dateFormat.parse ( d1sub );
            Date EndDate = dateFormat.parse ( d2sub );
            switch ( Term ) {
                case "0":

                case "1":

                case "2":

            }
            if ( ( date.getTime ( ) >= EndDate.getTime ( ) ) ) {
                Toast.makeText ( getApplicationContext ( ), d1 + "\n" + d2, Toast.LENGTH_LONG ).show ( );
            }

        } catch ( Exception e ) {
            e.printStackTrace ( );
        }
        return PaidStatus;
    }

    private
    void Closeapp ( ) {
        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ) {
            builder = new AlertDialog.Builder ( MainActivity.this );
        } else {
            builder = new AlertDialog.Builder ( MainActivity.this, AlertDialog.BUTTON_NEUTRAL );
        }
        builder.setTitle ( "Thank You" );
        builder.setMessage ( "Thank You For Using Our Application Please Give Us Your Suggestions and Feedback " );
        builder.setNegativeButton ( "RATE US",
                                    new DialogInterface.OnClickListener ( ) {
                                        public
                                        void onClick (
                                                DialogInterface dialog,
                                                int which
                                        ) {
                                            Intent intent = new Intent ( Intent.ACTION_VIEW );
                                            intent.setData ( Uri.parse ( "https://play.google.com/store/apps/details?id=com.eteach.epathshalaassist" ) ); // Add package name of your application
                                            startActivity ( intent );
                                            Toast.makeText ( MainActivity.this, "Thank you for your Rating",
                                                             Toast.LENGTH_SHORT ).show ( );
                                        }
                                    } );
        builder.setPositiveButton ( "QUIT",
                                    new DialogInterface.OnClickListener ( ) {
                                        public
                                        void onClick (
                                                DialogInterface dialog,
                                                int which
                                        ) {
                                            finishAffinity ( );
                                        }
                                    } );

        builder.show ( );
    }

    @Override
    public
    boolean onCreateOptionsMenu ( Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if ( navItemIndex == 0 ) {
            getMenuInflater ( ).inflate ( R.menu.menu_main, menu );
        }
        // when fragment is notifications, load the menu created for notifications
        return true;
    }

    @Override
    public
    boolean onOptionsItemSelected ( MenuItem item ) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ( );

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }*/

        return super.onOptionsItemSelected ( item );
    }

    private
    void toggleFab ( ) {
        if ( navItemIndex == 0 && Constants.navItemIndex == 0 )
            fab.show ( );
        else
            fab.hide ( );
    }

    private
    class DownloadEvents extends AsyncTask {
        @Override
        protected
        void onPreExecute ( ) {
            pDialog = new SweetAlertDialog ( MainActivity.this, SweetAlertDialog.PROGRESS_TYPE );
            pDialog.setTitleText ( "Sychronizing Data...." )
                    .setContentText ( "ePathShala School Assist" )
                    .show ( );
            super.onPreExecute ( );
        }

        @Override
        protected
        Object doInBackground ( Object[] params ) {
            try {
                EventWebservices eventWebservices = new EventWebservices ( getApplicationContext ( ) );
                eventWebservices.GETALLEVENTS ( 3 );
            } catch ( Exception e ) {
                e.printStackTrace ( );
            }
            try {
                Fees_Webservices fees_webservices = new Fees_Webservices ( getApplicationContext ( ) );
                fees_webservices.GETALLFEES ( user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_NO ), 3 );
            } catch ( Exception e ) {
                e.printStackTrace ( );
            }
            try {
                ExamReportCardWebservices examReportCardWebservices = new ExamReportCardWebservices ( getApplicationContext ( ) );
                examReportCardWebservices.GETALLREPORTCARD ( user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_NO ) );
            } catch ( Exception e ) {
                e.printStackTrace ( );
            }
            try {
                ToppersWebservices toppersWebservices = new ToppersWebservices ( getApplicationContext ( ) );
                toppersWebservices.GETALLTOPPERS ( );
            } catch ( Exception e ) {
                e.printStackTrace ( );
            }
            try {
                user = utilitys.getuserandclass ( );
                NotificationsWebservices notificationsWebservices = new NotificationsWebservices ( getApplicationContext ( ) );
                // notificationsWebservices.GETALLNOTIFICATIONS(user.get( utilitys.KEY_STD_CLASS_ID) ,user.get( utilitys.KEY_STD_ID));
                notificationsWebservices.GETALLNOTIFICATIONS ( user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_CLASS_ID ), user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_ID ), 3 );
            } catch ( Exception e ) {
                e.printStackTrace ( );
            }
            try {
                AttendanceWebservices attendanceWebservices = new AttendanceWebservices ( getApplicationContext ( ) );
                String date = new SimpleDateFormat ( "yyyy-MM-dd" ).format ( Calendar.getInstance ( ).getTime ( ) );
                attendanceWebservices.GETALLATTENDANCE ( ( user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_NO ) ), date, 3 );//query 3 for update data
            } catch ( Exception e ) {
                e.printStackTrace ( );
            }
            try {
                HomeworkWebservices homeworkWebservices = new HomeworkWebservices ( getApplicationContext ( ) );
                homeworkWebservices.GetHomeworklist ( ( user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_CLASS_ID ) ), 3 );
            } catch ( Exception e ) {
                e.printStackTrace ( );
            }
            try{
                SyllabusWebservices syllabusWebservices = new SyllabusWebservices ( getApplicationContext());
                syllabusWebservices.GETALLSYLABUS ( Integer.parseInt ( ( user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_CLASS_ID )) ), 3);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            /*try{
                TimeTableWebservices timeTableWebservices = new TimeTableWebservices ( getApplicationContext());
                timeTableWebservices.GETALLTIMETABLE ( ( user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_CLASS_ID )) , 3);
            }catch (Exception e)
            {
                e.printStackTrace();
            }*/
            return 0;
        }

        @Override
        protected
        void onPostExecute ( Object o ) {
            new Handler ( ).postDelayed ( new Runnable ( ) {
                @Override
                public
                void run ( ) {
                    pDialog.dismissWithAnimation ( );
                }
            }, 5000 );
            super.onPostExecute ( o );
        }
    }

    @Override
    protected
    void onResume ( ) {
        super.onResume ( );

    }

    @Override
    protected
    void onStop ( ) {
        super.onStop ( );
        //stopService ( new Intent ( this,LocationService.class ) );
    }

    public
    void showUpdateDialog ( ) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder ( MainActivity.this );

        alertDialogBuilder.setTitle ( MainActivity.this.getString ( R.string.app_name ) );
        alertDialogBuilder.setMessage ( MainActivity.this.getString ( R.string.update_message ) );
        alertDialogBuilder.setCancelable ( false );
        alertDialogBuilder.setPositiveButton ( R.string.update_now, new DialogInterface.OnClickListener ( ) {
            public
            void onClick ( DialogInterface dialog, int id ) {
                MainActivity.this.startActivity ( new Intent ( Intent.ACTION_VIEW, Uri.parse ( "market://details?id=" + getPackageName ( ) ) ) );
                dialog.cancel ( );
            }
        } );
        alertDialogBuilder.setNegativeButton ( R.string.cancel, new DialogInterface.OnClickListener ( ) {
            @Override
            public
            void onClick ( DialogInterface dialog, int which ) {
                if ( isForceUpdate ) {
                    finish ( );
                }
                dialog.dismiss ( );
            }
        } );
        alertDialogBuilder.show ( );
    }

    @Override
    protected
    void onDestroy ( ) {
        super.onDestroy ( );
        //stopService ( new Intent ( this,LocationService.class ) );
    }

    private
    class FCM_Registration extends AsyncTask <Void, Void, Boolean> {
        String result;

        @Override
        protected
        Boolean doInBackground ( Void... voids ) {
            loginWebservice = new LoginWebservice ( getApplicationContext ( ) );
            try {
                if ( constants.deviceid == "" ) {
                    result = loginWebservice.Register_FCM ( constants.STUDENT_SESSION_ID, Integer.valueOf ( constants.STUDENT_CLASS_ID ), Integer.valueOf ( constants.STUDENT_ID ),
                                                            user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_NO ), Settings.Secure.getString ( getContentResolver ( ), Settings.Secure.ANDROID_ID ), FCM_token, true, 3 );
                    if ( result.equals ( "OK" ) ) {
                        return true;
                    }
                } else {
                    result = loginWebservice.Register_FCM ( constants.STUDENT_SESSION_ID, Integer.valueOf ( constants.STUDENT_CLASS_ID ), Integer.valueOf ( constants.STUDENT_ID ),
                                                            user.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_NO ), constants.deviceid, FCM_token, true, 3 );
                }
                if ( result.equals ( "OK" ) ) {
                    return true;
                }
            } catch ( Exception ex ) {
                constants.error = ex.toString ( );
                Constants.FCMStoredOnServer = 0;
                return false;
            }
            return Boolean.valueOf ( result );
        }

        @Override
        protected
        void onPostExecute ( Boolean aBoolean ) {
            super.onPostExecute ( aBoolean );
            if ( aBoolean == true ) {
                Constants.FCMStoredOnServer = 1;
            }
        }
    }
}

