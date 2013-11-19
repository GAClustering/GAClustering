package gaclustering;
//************ Const for SAX ************************


public class Const {

//*********** constant **********************
public static int SLACK_GEN				   = 50 ;
public static int POPULATION			   = 100;
				   
//*********** percent of operation *************
public static int PERCENT_SINGLE	 = 5;
public static int PERCENT_CROSSOVER	   = 90;
public static int PERCENT_REPRODUCTION   = 5;
public static int PERCENT_MUTATION       = 10;

//Number of cluster

//
public static int TOTAL_RECORDS  = 100;
// Similarity Matrix
public static Double[][] SimilarityMatrix;

public static int NUM_OF_CLUSTER = 2;
public static int CHROMOSOME_LENGTH = 6;
public static int MEDROID_LENGTH = 2;


public static int countInvalid =0;
}






/************ Const for StudentData ************************
public class Const {

//*********** constant **********************
public static int MAXGEN				   = 500 ;
public static int POPULATION			   = 40;
public static int NUM_OF_CLASS		       = 3;
public static final int MAXSIMULATION      = 100;
public static final int MAXPROGSIZE        = 100;
public static int MAXRANGE				   =0;
//*********** percent of operation *************
public static int PERCENT_SINGLE		   = 25;
public static int PERCENT_CROSSOVER	   = 25;
public static int PERCENT_REPRODUCTION   = 25;
public static int PERCENT_MUTATION       = 25;
//*********** percent of fitness operation *************
public static final int PERCENT_FROM_WEIGHT    = 10;
public static final int PERCENT_FROM_BOOLEAN   = 90;
//*********** acceptable percent of each GP nade *****
public static final  float PERCENT_CLASS1      = 100;
public static final  float PERCENT_CLASS2      = 100;
public static final  float PERCENT_CLASS3      = 100;
public static final  float PERCENT_CLASS4      = 100;
// robot condition
public static int N_CONDITION            = 26;  // 26 in case of STUDENT DATA , 52 in case of HeartData
public static final int N_DOING	               = 0;
public static final int N_OPERATION            = 3;
public static final int N_UPPERCASE            = 26;

//robot operation
public static int  OP_1	= Const.N_CONDITION;	//IFAND   26 in case of STUDENT DATA , 52 in case of HeartData
public static int  OP_2	= Const.N_CONDITION+1;	//IFOR      27 in case of STUDENT DATA , 53 in case of HeartData
public static int  OP_3	= Const.N_CONDITION+2;	//IFNOT   28 in case of STUDENT DATA , 54 in case of HeartData
}

//*//////////////////////////////////////////////////////////////////////



/*

//************ Const for HeartData ************************
public class Const {

//*********** constant **********************
public static final int MAXGEN	               = 5000;
public static final int POPULATION             = 40;
public static final int MAXSIMULATION          = 100;
public static final int MAXPROGSIZE            = 100;
//*********** percent of operation *************
public static final int PERCENT_SINGLE  = 25;
public static final int PERCENT_CROSSOVER  = 25;
public static final int PERCENT_REPRODUCTION = 25;
public static final int PERCENT_MUTATION   =25;
//*********** percent of fitness operation *************
public static final int PERCENT_FROM_WEIGHT   = 10;
public static final int PERCENT_FROM_BOOLEAN   = 90;
//*********** acceptable percent of each GP nade *****
public static final  float PERCENT_CLASS1 = 100;
public static final  float PERCENT_CLASS2 = 100;
public static final  float PERCENT_CLASS3 =  100;
public static final  float PERCENT_CLASS4 = 100;
// ********** robot command *****************
public static final int N_CONDITION = 52;  // 26 in case of STUDENT DATA , 52 in case of HeartData
public static final int N_DOING	  = 0;
public static final int N_OPERATION = 3;
public static final int  N_UPPERCASE = 26;
//robot operation
public static final int  OP_1	= 52;	//IFAND   26 in case of STUDENT DATA , 52 in case of HeartData
public static final int  OP_2	= 53;	//IFOR      27 in case of STUDENT DATA , 53 in case of HeartData
public static final int  OP_3	= 54;	//IFNOT   28 in case of STUDENT DATA , 54 in case of HeartData
}

//************ Const for iRIS ************************
public class Const {

//*********** constant **********************
public static final int MAXGEN	               = 50000;
public static final int POPULATION             = 40;
public static final int MAXSIMULATION          = 100;
public static final int MAXPROGSIZE            = 100;
public static final int MAXRANGE               = 100;
//*********** percent of operation *************
public static final int PERCENT_SINGLE  = 25;
public static final int PERCENT_CROSSOVER  = 25;
public static final int PERCENT_REPRODUCTION = 25;
public static final int PERCENT_MUTATION   =25;
//*********** percent of fitness operation *************
public static final int PERCENT_FROM_WEIGHT   = 10;
public static final int PERCENT_FROM_BOOLEAN   = 90;
//*********** acceptable percent of each GP nade *****
public static final  float PERCENT_CLASS1 = 100;
public static final  float PERCENT_CLASS2 = 100;
public static final  float PERCENT_CLASS3 =  100;
public static final  float PERCENT_CLASS4 = 100;
// ********** robot command *****************
public static final int N_CONDITION = 12;
public static final int N_DOING	  = 0;
public static final int N_OPERATION = 3;
public static final int  N_UPPERCASE = 26;
//robot operation
public static final int  OP_1	= 12;
public static final int  OP_2	= 13;
public static final int  OP_3	= 14;
}

//************ Const for Balance_Scale ************************
public class Const {

//*********** constant **********************
public static final int MAXGEN	               = 5000;
public static final int POPULATION             = 40;
public static final int MAXSIMULATION          = 100;
public static final int MAXPROGSIZE            = 100;
public static final int MAXRANGE               = 100;
//*********** percent of operation *************
public static final int PERCENT_SINGLE  = 25;
public static final int PERCENT_CROSSOVER  = 25;
public static final int PERCENT_REPRODUCTION = 25;
public static final int PERCENT_MUTATION   =25;
//*********** percent of fitness operation *************
public static final int PERCENT_FROM_WEIGHT   = 10;
public static final int PERCENT_FROM_BOOLEAN   = 90;
//*********** acceptable percent of each GP nade *****
public static final  float PERCENT_CLASS1 = 100;
public static final  float PERCENT_CLASS2 = 100;
public static final  float PERCENT_CLASS3 =  100;
public static final  float PERCENT_CLASS4 = 100;
// ********** robot command *****************
public static final int N_CONDITION = 20;
public static final int N_DOING	  = 0;
public static final int N_OPERATION = 3;
public static final int  N_UPPERCASE = 26;
//robot operation
public static final int  OP_1	= 20;
public static final int  OP_2	= 21;
public static final int  OP_3	= 22;
}

//************ Const for HyrData ************************

public class Const {

//*********** constant **********************
public static final int MAXGEN	           = 100000;
public static final int POPULATION             = 60;
public static final int MAXSIMULATION          = 100;
public static final int MAXPROGSIZE            = 100;
public static final int MAXRANGE               = 200;
//*********** percent of operation *************
public static final int PERCENT_SINGLE       = 25;
public static final int PERCENT_CROSSOVER    = 25;
public static final int PERCENT_REPRODUCTION = 25;
public static final int PERCENT_MUTATION   =25;
//*********** percent of fitness operation *************
public static final int PERCENT_FROM_WEIGHT   = 10;
public static final int PERCENT_FROM_BOOLEAN   = 90;
//*********** acceptable percent of each GP nade *****
public static final  float PERCENT_CLASS1 = 100;
public static final  float PERCENT_CLASS2 = 100;
public static final  float PERCENT_CLASS3 =  100;
public static final  float PERCENT_CLASS4 = 100;
// robot condition
public static final int N_CONDITION = 15;
public static final int N_DOING	  = 0;
public static final int N_OPERATION = 3;
public static final int  N_UPPERCASE = 26;

//robot operation
public static final int  OP_1	= 15;
public static final int  OP_2	= 16;
public static final int  OP_3	= 17;
}
*/