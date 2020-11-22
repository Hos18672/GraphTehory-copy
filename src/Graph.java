import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Graph {
    private int size;
    private int radius, durchmesser =0;
    private String zentrum;
    public  Integer AdjacencyMatrix2[][];
    private Integer distanceMatix[][];
    private Integer wegmatrix[][];
    private Integer matrixA[][];
    private int exzentrizitaet[];
    private int posUnique[];
    private int time = 0;
    private static final int NIL = -1;
    private ArrayList<ArrayList<Integer>> adjListArray = new ArrayList<>();
    private String inputLine = "";

    String apPrint = "";
    String bridgePrint = "";
    private String filelocation, path = null;
    JMenuItem newMenuItem = new JMenuItem("New") {public void menuSelectionChanged(boolean isSelected) {
        super.menuSelectionChanged(isSelected);

        if (isArmed()) {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("select File");
            fc.showOpenDialog(null);
            File file = fc.getSelectedFile();
            path =file.getAbsolutePath();

            System.out.println("The Path" + file.getPath());
        } else {
            System.out.println("The menu item is no longer selected");
        }
    }};
    //-----------------------Getter-------------------------------------------------

    public Integer[][] getAdjacencyMatrix2() {
        return AdjacencyMatrix2;
    }
    public Integer[][] getDistanceMatix() {
        return distanceMatix;
    }
    public Integer[][] getWegmatrix() {
        return wegmatrix;
    }
    public String getPath() {
        if (filelocation == null) {
            filelocation = "F:\\Schule\\OneDrive - Erudio School of Art\\Desktop\\input_graph1.csv" ;
        } else {
            filelocation = path;
        }
        return filelocation;
    }
    public String getZentrum() {
        return zentrum;
    }
    public int getRadius() {
        return radius;
    }
    public int getDurchmesser() {
        return durchmesser;
    }
    //-----------------------Setter-----------------------------------------------
    public void setSize(int size) {
        this.size = size;
    }
    public void setZentrum(String zentrum) {
        this.zentrum = zentrum;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    public void setDurchmesser(int durchmesser) {
        this.durchmesser = durchmesser;
    }
    public void setAdjListArray(ArrayList<ArrayList<Integer>> adjListArray) {
        this.adjListArray = adjListArray;
    }

    public Integer[][] readCSVFile() {
        Integer[][] myArray = null;
        System.out.println("....");
        try{   //setup a scanner
            Scanner scannerIn = new Scanner(new BufferedReader(new FileReader(getPath())));
            int j=0;
            while (scannerIn.hasNextLine())
            {
                // read line in from file
                inputLine = scannerIn.nextLine().trim();
                if(inputLine.endsWith(",")) {
                    inputLine = inputLine.substring(0, inputLine.length()-1);
                }
                // split the Inputline into an array at the commas
                String[] inArray = inputLine.split(",");
                if(myArray == null) {
                    myArray = new Integer[inArray.length][inArray.length];
                }
                //copy the content of the inArray to the myArray
                for (int i =0; i < inArray.length; i++)
                {
                    if(!inArray[i].trim().isEmpty()) {
                        myArray[j][i] = Integer.parseInt(inArray[i].trim());
                    }
                }
                // Increment the row in the array
                j++;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return myArray;
    }
    public void printGraph() {
        System.out.println("\n------------ Print AdjacencyMatrix ---------------------");
        for (int i = 0; i < size; i++) {
            System.out.println();
            for (int j = 0; j < size; j++) {
                System.out.print(AdjacencyMatrix2[i][j]+ " ");
            }
        }
    }
    public void initAll() {
        initialize();
        printGraph();
        ermittle();
        PrintDistanceMatrix();
        exzentrizitaet();
        radiusUndDurchmesser();
        zentrum();
        komponentenanzahl();
        AP();
        bridge();
    }
    public void initAll2() {
        //printGraph();
        ermittle();
        multiply();
        PrintDistanceMatrix();
        exzentrizitaet();
        radiusUndDurchmesser();
        zentrum();
        komponentenanzahl();
        AP();
        bridge();
    }
    public void initialize() {
        Integer[][] loadedMtrix = readCSVFile();
        size = loadedMtrix.length;
        AdjacencyMatrix2 = new Integer[size][size];
        distanceMatix = new Integer[size][size];
        wegmatrix = new Integer[size][size];
        matrixA = new Integer[size][size];
        exzentrizitaet = new int[size];
        posUnique = new int [size];

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                AdjacencyMatrix2[i][j] =loadedMtrix[i][j];
                distanceMatix[i][j] =loadedMtrix[i][j];
                matrixA[i][j] =loadedMtrix[i][j];
                if (i != j){
                    if (distanceMatix[i][j]==0){
                        distanceMatix[i][j]= -1;
                        wegmatrix[i][j] = 0;
                    }
                    else {
                        wegmatrix[i][j] = 1;
                    }

                }
                else {
                    wegmatrix[i][j] = 1;
                }
            }
        }
    }
    public void initialize2(JButton[][] loadedMtrix2) {

        size = loadedMtrix2.length;
        AdjacencyMatrix2 = new Integer[size][size];
        distanceMatix = new Integer[size][size];
        wegmatrix = new Integer[size][size];
        matrixA = new Integer[size][size];
        exzentrizitaet = new int[size];
        posUnique = new int [size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                AdjacencyMatrix2[i][j] =Integer.parseInt(loadedMtrix2[i][j].getText());
                distanceMatix[i][j] =Integer.parseInt(loadedMtrix2[i][j].getText());
                matrixA[i][j] =Integer.parseInt(loadedMtrix2[i][j].getText());
                if (i != j){
                    if (distanceMatix[i][j]==0){
                        distanceMatix[i][j]= -1;
                        wegmatrix[i][j] = 0;
                    }
                    else {
                        wegmatrix[i][j] = 1;
                    }
                }
                else {
                    wegmatrix[i][j] = 1;
                }
            }
        }
    }
    public void ermittle() {
        int anzMultipliziert = 0;
        while (anzMultipliziert < size) {
            Integer[][] multiply = multiply();
            anzMultipliziert++;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (distanceMatix[i][j] < 0 && multiply[i][j] > 0) {
                        distanceMatix[i][j] = anzMultipliziert + 1;
                    } else if (wegmatrix[i][j] == 0 && multiply[i][j] > 0) {
                        wegmatrix[i][j] = 1;
                    }
                }
            }
        }
    }

    public Integer[][] multiply() {
        Integer multiply[][] = new Integer[size][size];
        int sum = 0;
        for (int row = 0; row < size ; row++) {
            for (int col = 0; col < size ; col++) {
                sum =0;
                for (int index = 0; index < size; index++) {
                    sum = sum + matrixA[row][index] * AdjacencyMatrix2[index][col];
                }
                multiply[row][col] = sum;
            }
        }
        for(int i = 0; i < matrixA.length; i++){
            for(int j = 0; j < matrixA.length; j++){
                matrixA[i][j] = multiply[i][j];
            }
        }
        return multiply;
    }
    public void PrintDistanceMatrix() {
        System.out.println("\n\n------------ Print DistanceMatrix---------------------\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(distanceMatix[i][j] +" ");
            }
            System.out.println();
        }
    }
    public void radiusUndDurchmesser(){
        this.radius = 999;
        this.durchmesser =0;
        for (int i = 0; i < exzentrizitaet.length; i++){
            if (exzentrizitaet[i] < radius && exzentrizitaet[i] > -1 ){
                radius = exzentrizitaet[i];
                setRadius(radius);
            }
            if (exzentrizitaet[i] > durchmesser){
                this.durchmesser = exzentrizitaet[i];
                setDurchmesser(this.durchmesser);
            }
        }
        System.out.println("\n------------ Print Radius ---------------------\n");
        System.out.println("Radius: " +radius);
        System.out.println("\n------------ Print Durchmesser ---------------------\n");
        System.out.println("Durchmesser: " +durchmesser);
    }
    public String exzentrizitaet(){
        int max = 0;
        String info = "{ ";
        for (int i = 0; i < distanceMatix.length; i++){
            for (int j = 0; j < distanceMatix.length; j++){
                if (max < distanceMatix[i][j]){
                    max = distanceMatix[i][j];
                }
            }
            exzentrizitaet[i] = max;
            max = 0;
        }
        System.out.println("\n------------ Print Exzentrizitaet---------------------\n");

        for(int i =0; i < exzentrizitaet.length; i++ )
        {
            info += exzentrizitaet[i] + " ";

        }
        info = info+ "} \n";
        System.out.print(info);
        return info;
    }
    public String zentrum(){
        String info;
        int zentrum[];
        zentrum = new int[exzentrizitaet.length];
        for (int i = 0; i < exzentrizitaet.length; i++){
            if (exzentrizitaet[i] == this.radius){
                zentrum[i] = i+1;
            }
        }
        info = "{";
        for (int i = 0; i < zentrum.length; i++){
            if (zentrum[i] != 0){
                info = info + " " + zentrum[i];
            }
        }
        info = info + " " + " }";
        System.out.println("\n------------ Print Zentrum ---------------------\n");
        System.out.println(info);
        setZentrum( info);
        return info;
    }
    public boolean isZusammenhaengend(){
        int count = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (wegmatrix[i][j]==1){
                    count++;
                }
            }
        }
        if (count == size*size){
            return true;
        }
        return false;
    }
    public int komponentenanzahl(){
        int vergleichsArray[] = new int [size];
        int pos[]= new int [size];
        int count = 0;
        int index = 0;
        //Position ermitteln
        while (index < size){
            //Zeile in vergleichsArray speichern
            for (int j = 0; j < size; j++){
                vergleichsArray[j] = wegmatrix[index][j];
            }
            //Position speichern
            for (int i = 0; i < size; i++){
                count = 0;
                for (int j = 0; j < size; j++){
                    if (wegmatrix[i][j] == vergleichsArray[j]){
                        count++;
                        if (count == size){
                            pos[index] = i+1;
                        }
                    }
                }
            }
            index++;
        }
        //Elemente in pos in setString speichern und dadurch "Duplikate entfernen"
        Set<Integer> setString = new LinkedHashSet<Integer>();
        for(int i=0;i<pos.length;i++){
            setString.add(pos[i]);
        }
        //Für Komponentenbeschreibung //Elemente in setString in posUnique speichern
        int ind = 0;
        for (Iterator<Integer> it = setString.iterator(); it.hasNext(); ) {
            posUnique[ind] = it.next();
            ind++;
        }
        //Komponentenanzahl zurückgeben
        return setString.size();
    }
    public String komponenteAusgeben(){
        String infKompo="";
        int komponent = 1;
        for (int i = 0; i < komponentenanzahl(); i++){
            infKompo+= "K"+komponent+" { ";
            for (int a = 0; a < wegmatrix.length; a++){
                for (int b = 0; b < wegmatrix.length; b++){
                    if (posUnique[i]-1 == a){
                        if (wegmatrix[a][b]==1){
                            infKompo += b+1 + " ";
                        }
                    }
                }
            }
            infKompo += "}\n";
            komponent++;
        }
        return infKompo;
    }
    //----------------------ArrayList -----------------------------------------------
    public static ArrayList<ArrayList<Integer>> convert(Integer[][] a) {
        // no of vertices
        int l = a[0].length;
        ArrayList<ArrayList<Integer>> adjListArray
                = new ArrayList<ArrayList<Integer>>(l);
        // Create a new list for each
        // vertex such that adjacent
        // nodes can be stored
        for (int i = 0; i < l; i++) {
            adjListArray.add(new ArrayList<Integer>());
        }
        int i, j;
        for (i = 0; i < a[0].length; i++) {
            for (j = 0; j < a.length; j++) {
                if (a[i][j] == 1) {
                    adjListArray.get(i).add(j);
                }
            }
        }
        return adjListArray;
    }
    public static void printArrayList(ArrayList<ArrayList<Integer>> adjListArray){
        // Print the adjacency list
        for (int v = 0; v < adjListArray.size(); v++) {
            System.out.print(v);
            for (Integer u : adjListArray.get(v)) {
                System.out.print(" -> " + u);
            }
            System.out.println();
        }
    }
    //----------------- Articulation-Point-------------------------------
    public void APUtil(int u, boolean visited[], int disc[], int low[], int parent[], boolean ap[]) {
        setAdjListArray(convert(getAdjacencyMatrix2()));
        // Count of children in DFS Tree
        int children = 0;
        // Mark the current node as visited
        visited[u] = true;
        // Initialize discovery time and low value
        disc[u] = low[u] = ++time;
        // Go through all vertices aadjacent to this
        Iterator<Integer> i = adjListArray.get(u).iterator();
        while (i.hasNext())
        {
            int v = i.next();  // v is current adjacent of u
            // If v is not visited yet, then make it a child of u
            // in DFS tree and recur for it
            if (!visited[v])
            {
                children++;
                parent[v] = u;
                APUtil(v, visited, disc, low, parent, ap);
                // Check if the subtree rooted with v has a connection to
                // one of the ancestors of u
                low[u]  = Math.min(low[u], low[v]);
                // u is an articulation point in following cases
                // (1) u is root of DFS tree and has two or more chilren.
                if (parent[u] == NIL && children > 1)
                    ap[u] = true;
                // (2) If u is not root and low value of one of its child
                // is more than discovery value of u.
                if (parent[u] != NIL && low[v] >= disc[u])
                    ap[u] = true;
            }
            // Update low value of u for parent function calls.
            else if (v != parent[u])
                low[u]  = Math.min(low[u], disc[v]);
        }
    }
    // The function to do DFS traversal. It uses recursive function APUtil()
    public void AP() {
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[size];
        int disc[] = new int[size];
        int low[] = new int[size];
        int parent[] = new int[size];
        boolean ap[] = new boolean[size]; // To store articulation points
        // Initialize parent and visited, and ap(articulation point)
        // arrays
        for (int i = 0; i < size; i++)
        {
            parent[i] = NIL;
            visited[i] = false;
            ap[i] = false;
        }
        // Call the recursive helper function to find articulation
        // points in DFS tree rooted with vertex 'i'
        for (int i = 0; i < size; i++)
            if (visited[i] == false)
                APUtil(i, visited, disc, low, parent, ap);
        // Now ap[] contains articulation points, print them
        for (int i = 0; i < size; i++)
            if (ap[i] == true) {
                apPrint += i + "  ";
                System.out.print(i + " ");
            }
    }
    //---------------------Bridge------------------------------------
    public void bridgeUtil(int u, boolean visited[], int disc[], int low[], int parent[]) {
        // Mark the current node as visited
        visited[u] = true;
        // Initialize discovery time and low value
        disc[u] = low[u] = ++time;
        // Go through all vertices aadjacent to this
        Iterator<Integer> i = adjListArray.get(u).iterator();
        while (i.hasNext())
        {
            int v = i.next();  // v is current adjacent of u
            // If v is not visited yet, then make it a child
            // of u in DFS tree and recur for it.
            // If v is not visited yet, then recur for it
            if (!visited[v])
            {
                parent[v] = u;
                bridgeUtil(v, visited, disc, low, parent);
                // Check if the subtree rooted with v has a
                // connection to one of the ancestors of u
                low[u]  = Math.min(low[u], low[v]);
                // If the lowest vertex reachable from subtree
                // under v is below u in DFS tree, then u-v is
                // a bridge
                if (low[v] > disc[u])
                    bridgePrint+= u+"-"+v +"   ";
                System.out.println(u+" "+v);
            }
            // Update low value of u for parent function calls.
            else if (v != parent[u])
                low[u]  = Math.min(low[u], disc[v]);
        }
    }
    // DFS based function to find all bridges. It uses recursive
    // function bridgeUtil()
    public void bridge() {
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[size];
        int disc[] = new int[size];
        int low[] = new int[size];
        int parent[] = new int[size];
        // Initialize parent and visited, and ap(articulation point)
        // arrays
        for (int i = 0; i < size; i++)
        {
            parent[i] = NIL;
            visited[i] = false;
        }
        // Call the recursive helper function to find Bridges
        // in DFS tree rooted with vertex 'i'
        for (int i = 0; i < size; i++)
            if (visited[i] == false) {
                bridgeUtil(i, visited, disc, low, parent);
            }
    }
}