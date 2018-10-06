package com.example.jabas.connectsteel;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Collections;


public class Geometrico extends AppCompatActivity {

    public double dPar,dfPar,fuPar,fyPar,lPar,fuChapa,fyChapa,bChapa,hChapa,tChapa,Ng,Nq,Nq2,Sd,yg,yq,wu0,wu1,wu2,ya1,ya2,yq2,wu0b,wu1b,wu2b,RanTra,RagTra,ResBloco,An1,Ag1;
    public double cts = 1;
    public String testepaths = "";
    public String materialParafuso = "";
    public String materialchapaS = "";


    public boolean Protegido;
    public TextView testintent2,testresults;

    private static final String TAG = "Geometrico";

    private  Button nextpage4; /*declarei o botao */

    //add PointsGraphSeries of DataPoint type
    PointsGraphSeries<DataPoint> xySeries;

    private Button btnAddPt;

    private EditText mX,mY;

    GraphView mScatterPlot;

    //make xyValueArray global
    private ArrayList<XYValue> xyValueArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geometrico);
        getmyItent2();

        //declare variables in oncreate
/*add o botao nextpage */
        nextpage4=(Button) findViewById(R.id.bt_nextpagebutton4);
        nextpage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openResultado();

            }
        }); /*ate aqui*/

        btnAddPt = (Button) findViewById(R.id.btnAddPt);
        mX = (EditText) findViewById(R.id.numX);
        mY = (EditText) findViewById(R.id.numY);
        mScatterPlot = (GraphView) findViewById(R.id.scatterPlot);
        xyValueArray = new ArrayList<>();

        init();
    }

    private void getmyItent2 () {
        dPar = getIntent().getDoubleExtra("dPar",0);
        dfPar= getIntent().getDoubleExtra("dfPar",0);
        lPar =getIntent().getDoubleExtra("lPar",0);
        dPar =getIntent().getDoubleExtra("dPar",0);
        fuPar= getIntent().getDoubleExtra("fuPar",0);
        fyPar=getIntent().getDoubleExtra("fyPar",0);
        fuChapa=getIntent().getDoubleExtra("fuChapa",0);
        fyChapa=getIntent().getDoubleExtra("fyChapa",0);
        bChapa=getIntent().getDoubleExtra("bChapa",0);
        tChapa=getIntent().getDoubleExtra("tChapa",0);
        hChapa=getIntent().getDoubleExtra("hChapa",0);

        //Ng,Nq,Nq2,Sd,yg,yq,wu0,wu1,wu2,ya1,ya2,yq2,wu0b,wu1b,wu2b

        ya1=getIntent().getDoubleExtra("Ya1",0);
        ya2=getIntent().getDoubleExtra("Ya2",0);
        Ng=getIntent().getDoubleExtra("Ng",0);
        Nq=getIntent().getDoubleExtra("Nq",0);
        yg=getIntent().getDoubleExtra("Yg",0);
        yq=getIntent().getDoubleExtra("Yq",0);
        wu0=getIntent().getDoubleExtra("wu0",0);
        wu1=getIntent().getDoubleExtra("wu1",0);
        wu2=getIntent().getDoubleExtra("wu2",0);
        wu0b=getIntent().getDoubleExtra("wu0b",0);
        wu1b=getIntent().getDoubleExtra("wu1b",0);
        wu2b=getIntent().getDoubleExtra("wu2b",0);
        Nq2=getIntent().getDoubleExtra("Nq2",0);
        yq2=getIntent().getDoubleExtra("Yq2",0);
        Sd=getIntent().getDoubleExtra("Sd",0);

        Protegido= getIntent().getBooleanExtra("Protegido",false);

        materialParafuso=getIntent().getExtras().getString("materialxyz","");
        materialchapaS=getIntent().getExtras().getString("materialchs","");

    }

    private void show() {
        testintent2 = findViewById(R.id.tv_testIntent2);
        testresults= findViewById(R.id.tv_testeResult2);
        testintent2.setText("teste testoso do teste  "+testepaths);
        testresults.setText("Ran" +String.format("%.2f",RanTra)+"KN"  + " " + "Rag" +String.format("%.2f",RagTra)  + "ResBloco" + String.format("%.2f",ResBloco));


        Log.d(TAG,"tste do teste do teste teste  " + testepaths);
    }

    private void init(){
        //declare the xySeries Object
        xySeries = new PointsGraphSeries<>();

        btnAddPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mX.getText().toString().equals("") && !mY.getText().toString().equals("") ){
                    double x = Double.parseDouble(mX.getText().toString());
                    double y = Double.parseDouble(mY.getText().toString());
                    if(x>hChapa/10|| y > bChapa/10) { //MODIFICADO EM 5/10 PARA NAO ADD PTS FORA DO LIMITE
                        toastMessage("Posição do conector fora dos limites da chapa");
                    } else {
                    Log.d(TAG, "onClick: Adding a new point. (x,y): (" + x + "," + y + ")" );
                    xyValueArray.add(new XYValue(x,y));
                    // nosso print
                    Log.d(TAG,"Aqui eh quando adiciona!");
                    for (int i = 0; i<xyValueArray.size();i++) {
                        Log.d(TAG,Double.toString(xyValueArray.get(i).getX()) + ", " + Double.toString(xyValueArray.get(i).getY()));

                    }
                    init(); }
                }else {
                    toastMessage("Preencha ambos os campos");
                }
            }
        });

        //little bit of exception handling for if there is no data.
        if(xyValueArray.size() != 0){
            createScatterPlot();
        }else{
            Log.d(TAG, "onCreate: No data to plot.");
        }
    }

    private void createScatterPlot() {
        Log.d(TAG, "createScatterPlot: Creating scatter plot.");

        //sort the array of xy values
        xyValueArray = sortArray(xyValueArray);
        Log.d(TAG,"Aqui eh quando plota!");
        for (int i = 0; i<xyValueArray.size();i++) {
            Log.d(TAG,Double.toString(xyValueArray.get(i).getX()) + ", " + Double.toString(xyValueArray.get(i).getY()));
        }


        //add the data to the series
        for(int i = 0;i <xyValueArray.size(); i++){
            try{
                double x = xyValueArray.get(i).getX();
                double y = xyValueArray.get(i).getY();
                xySeries.appendData(new DataPoint(x,y),true, 1000);
            }catch (IllegalArgumentException e){
                Log.e(TAG, "createScatterPlot: IllegalArgumentException: " + e.getMessage() );
            }
        }

        //set some properties
        xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeries.setColor(Color.GRAY);
        xySeries.setSize(20f);

        //set Scrollable and Scaleable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);

        //set manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(bChapa/10); /*get b */
        mScatterPlot.getViewport().setMinY(0);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(hChapa/10); /*get h*/
        mScatterPlot.getViewport().setMinX(0);

        mScatterPlot.addSeries(xySeries);
    }

    /**
     * Sorts an ArrayList<XYValue> with respect to the x values.
     * @param array
     * @return
     */
    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size() - 1;
        int count = 0;
      /*  Log.d(TAG, "sortArray: Sorting the XYArray.");*/


        while (true) {
            m--;
            if (m <= 0) {
                m = array.size() - 1;
            }
            //Log.d(TAG, "sortArray: m = " + m);
            try {
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m - 1).getY();
                double tempX = array.get(m - 1).getX();
                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                    //Log.d(TAG, "sortArray: count = " + count);
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                 // Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if (count == factor) {
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }

    /**
     *
     * @return
     */
    private ArrayList<Float> getColunasPorParafusos(){
        ArrayList<Float> colunas = new ArrayList<Float>();

        for(int i = 0; i < this.xyValueArray.size(); i++){
            if(!colunas.contains(new Float(this.xyValueArray.get(i).getX() * 10))){
                colunas.add(new Float(this.xyValueArray.get(i).getX() * 10));
            }
        }
        return colunas;
    }

    /**
     *
     * @param coluna
     * @return
     */
    private ArrayList<Float> getParafusosPorColunas(Float coluna){
        ArrayList<Float> parafusos = new ArrayList<Float>();
        for(int i = 0; i < this.xyValueArray.size(); i++){
            if(coluna == this.xyValueArray.get(i).getX() * 10){
                parafusos.add(new Float(xyValueArray.get(i).getY() * 10));
            }
        }
        return parafusos;
    }

    /**
     *
     * @param nome
     * @param b
     * @param t
     * @param h
     * @param matChapa
     * @param furacao
     * @param isParafusoComun
     * @param d
     * @param matParafuso
     * @param l
     * @param furacaoParafuso
     * @return
     */
    private Chapa gerarChapa(String nome, float b, float t, float h, String matChapa, String furacao, boolean isParafusoComun, float d, boolean matParafuso, float l, double furacaoParafuso, boolean protecao){

        //Verifica o tipo de parafuso

        if (materialParafuso == "A307") {
            isParafusoComun=true;

        } else {
            isParafusoComun=false;
        }


        ArrayList<Float> colunas = getColunasPorParafusos();
        Collections.sort(colunas);

        Chapa chapa = new Chapa(nome, b, t, h, matChapa, furacao, protecao);

        for (int i = 0; i < colunas.size(); i++){
            chapa.getColunas().add(new Coluna(colunas.get(i), Integer.toString(i)));
        }

        for(int i = 0; i < colunas.size(); i++){

            ArrayList<Float> parafuso = getParafusosPorColunas(colunas.get(i));

            parafuso.add(new Float(0));
            parafuso.add(chapa.getB());
            Collections.sort(parafuso);
            for(int j = 0 ; j < parafuso.size(); j++){

                float x = chapa.getColunas().get(i).getX();
                float y =  parafuso.get(j);
                String label = chapa.getColunas().get(i).getName() + (chapa.getColunas().get(i).getParafusos().size() + 1);
                Parafuso p = null;
                if(isParafusoComun){
                    p = new ParafusoComum(x, y, label, d, isParafusoComun, matParafuso, l, furacaoParafuso);
                }else{
                    p = new ParafusoAltaResistencia(x, y, label, d, isParafusoComun, matParafuso, l, furacaoParafuso);
                }

                if(p != null) chapa.getColunas().get(i).getParafusos().add(p);
            }
            parafuso.clear();
        }
        return chapa;
    }

    private Graph gerarGrafo(Chapa c){
        ArrayList<Parafuso> parafusos = new ArrayList<Parafuso>();

        for(int i = 0; i < c.getColunas().size(); i++){
            for(int j = 0; j < c.getColunas().get(i).getParafusos().size(); j ++){
                parafusos.add(c.getColunas().get(i).getParafusos().get(j));
            }
        }

        // Gera o grafo.
        Graph G = new Graph(parafusos.size());

        // Popula o grafo segundo as restrições do livro.
        for(int i = 0; i < parafusos.size(); i++){
            for(int j = 0; j < parafusos.size(); j ++){
                // Se ta na mesma coluna so conecta com o de baixo.
                if(parafusos.get(i).getX() == parafusos.get(j).getX() && j == i + 1){
                    if(i != j) G.addEdge(i, j);
                    continue;
                }
                // Se for a ultima coluna so conecta com a penultima.
                if(Integer.parseInt(parafusos.get(i).getLabel()) > (c.getColunas().size() - 1 )*10){
                    if(Integer.parseInt(parafusos.get(j).getLabel()) < (c.getColunas().size() - 1 )*10 && Integer.parseInt(parafusos.get(j).getLabel()) > (c.getColunas().size() - 2 )*10){
                        if(parafusos.get(i).getY() < parafusos.get(j).getY()){ // So conecta com parafusos abaixo
                            if(i != j) G.addEdge(i, j);
                        }
                    }
                }

                // Se for a primeira coluna so conecta com a segunda.
                if(Integer.parseInt(parafusos.get(i).getLabel()) < 10){
                    if(Integer.parseInt(parafusos.get(j).getLabel()) < 20 && Integer.parseInt(parafusos.get(j).getLabel()) > 10){
                        if(parafusos.get(i).getY() < parafusos.get(j).getY()){ // So conecta com parafusos abaixo
                            if(i != j) G.addEdge(i, j);
                        }
                    }
                }
                // Se é uma das colunas do meio da chapa.
                if(Integer.parseInt(parafusos.get(i).getLabel()) > 10 && Integer.parseInt(parafusos.get(i).getLabel()) < (c.getColunas().size() - 1 )*10){
                    int currentCol = Integer.parseInt(parafusos.get(i).getLabel())/10;

                    if((Integer.parseInt(parafusos.get(j).getLabel()) > (currentCol - 1 ) * 10 && Integer.parseInt(parafusos.get(j).getLabel()) < currentCol * 10) ||
                            (Integer.parseInt(parafusos.get(j).getLabel()) > currentCol * 10 && Integer.parseInt(parafusos.get(j).getLabel()) < (currentCol + 2 ) * 10)){ // se for um dos do meio conecta com seus vizinhos.
                        if(parafusos.get(i).getY() < parafusos.get(j).getY()){ // So conecta com parafusos abaixo
                            if(parafusos.get(i).getX() != parafusos.get(j).getX()) { // Tira o bug do 11
                                if (i != j) G.addEdge(i, j);
                                continue;
                            }
                        }
                    }
                }
            }
        }

        return G;
    }

    private Double gerarAllPaths(Chapa c, Graph G){

        ArrayList<Parafuso> parafusos = new ArrayList<Parafuso>();

        for(int i = 0; i < c.getColunas().size(); i++){
            for(int j = 0; j < c.getColunas().get(i).getParafusos().size(); j ++){
                parafusos.add(c.getColunas().get(i).getParafusos().get(j));
            }
        }

        //Pra rodar o paths é so tirar esses comentarios abaixo.
        ArrayList<AllPaths> allPaths = new ArrayList<AllPaths>();

        ArrayList<Double> calcPaths = new ArrayList<Double>();

        for(int i = 0; i < parafusos.size(); i++) {
            for (int j = 0; j < parafusos.size(); j++) {
                if(parafusos.get(i).getY() == 0 && parafusos.get(j).getY() == c.getB() && i != j){
                    AllPaths currPath = new AllPaths(G, i, j);
                    allPaths.add(currPath);
                    ArrayList<ArrayList<Integer>> paths = currPath.getPaths();
                    //printPaths(paths);
                    calcPaths.add(getMenorCamiho(c, paths, parafusos));
                }
            }
        }

        Collections.sort(calcPaths);

        for (int i =0; i<calcPaths.size(); i++) {
            testepaths +=" " + calcPaths.get(i);
        }

        double shortest = calcPaths.get(0);
        return shortest;
    }

    public double getMenorCamiho(Chapa c, ArrayList<ArrayList<Integer>> paths, ArrayList<Parafuso> parafusos){
        ArrayList<Double> calcPaths = new ArrayList<Double>();
        for (int i = 0; i < paths.size(); i++) {
            ArrayList<Integer> aux = paths.get(i);
            double sum = calcCaminho(c, aux, parafusos);
            calcPaths.add(sum);
        }

        Collections.sort(calcPaths);

        return calcPaths.get(0);
    }

    public double calcCaminho(Chapa c, ArrayList<Integer> path, ArrayList<Parafuso> parafusos){
        double sum = c.getB() - (path.size() - 2) * (dfPar); // colocar valor df
        for (int j = 0; j < path.size() - 1; j++) {
            if(parafusos.get(path.get(j)).getX() != parafusos.get(path.get(j + 1)).getX()){
                double s = Math.pow(parafusos.get(path.get(j)).getX() - parafusos.get(path.get(j + 1)).getX(), 2);
                double g = Math.abs(parafusos.get(path.get(j)).getY() - parafusos.get(path.get(j + 1)).getY());
                double div = s/(4 * g);// era s
                sum += div; // adição da parcela diagonal
            }
        }
        return sum;
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public  static  double AreaLiquida (double t,double shortest){
        double An=(t*shortest)/100;
        return An;
    }

    public static  double AreaBruta (double t, double b) {
        double Ag= (t*b)/100;
        return Ag;
    }

   public static double Resistencia(double A,double y,double f) {
        double R = A*f/y;
        return R;
    }





    // CALCULO CISALHAMENTO EM BLOCO

    public   double ResCisBloco (Chapa c, double ya2, double fu, double fy, double cts) {

        Parafuso maior = c.getColunas().get(0).getParafusos().get(1); /* cisalhamento para primeira linha */
        Parafuso menor = c.getColunas().get(0).getParafusos().get(1);
        for (int i = 0; i < c.getColunas().size(); i++) {
            if (c.getColunas().get(i).getParafusos().get(1).getY() < menor.getY())
                menor = c.getColunas().get(i).getParafusos().get(1);
        }
        for (int i = 0; i < c.getColunas().size(); i++) {
            if (c.getColunas().get(i).getParafusos().get(1).getX() > maior.getX())
                if(c.getColunas().get(i).getParafusos().get(1).getY()==menor.getY())
                    maior = c.getColunas().get(i).getParafusos().get(1);
        }

        Parafuso pultimalinhaY = c.getColunas().get(0).getParafusos().get(c.getColunas().get(0).getParafusos().size()-2); /* cisalhamento para ultima linha */
        Parafuso pultimalinhaX = c.getColunas().get(0).getParafusos().get(c.getColunas().get(0).getParafusos().size()-2);
        for (int i=0; i < c.getColunas().size(); i++){
            if(c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size()-2).getY()>pultimalinhaY.getY())
                pultimalinhaY = c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size()-2);
        }
        for (int i=0; i < c.getColunas().size(); i++){
            if (c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size()-2).getX()>pultimalinhaX.getX())
                if (c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size()-2).getY()==pultimalinhaY.getY())
                    pultimalinhaX=c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size()-2);
        }

        int naultimalinha = 0;
        for (int i=0;i< c.getColunas().size();i++){
            if(c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size()-2).getY()==pultimalinhaY.getY()) naultimalinha++;
        }
        double Anv2 = ((pultimalinhaX.getX() - ((naultimalinha - 1) * (dfPar)) - ((dfPar) / 2)) * c.getT()) / 100;

        double Ant2 = ((c.getB()- (pultimalinhaY.getY() )- (dfPar / 2))* c.getT()) / 100;

        double Agv2 = (pultimalinhaX.getX() * c.getT()) / 100;

        System.out.println(naultimalinha +" na ultima linha");
        System.out.println(pultimalinhaY.getY());
        System.out.println(pultimalinhaX.getX());
        System.out.println(Anv2);
        System.out.println(Ant2);
        System.out.println(Agv2);


        /* a partir daqui e original */
        int naLinha = 0;
        for (int i = 0; i < c.getColunas().size(); i++) {
            if (c.getColunas().get(i).getParafusos().get(1).getY() == menor.getY()) naLinha++;
        }

        double Anv1 = ((maior.getX() - ((naLinha - 1) * (dfPar)) - ((dfPar) / 2)) * c.getT()) / 100;

        double Ant1 = ((menor.getY() - ((dfPar) / 2)) * c.getT()) / 100;

        double Agv1 = (maior.getX() * c.getT()) / 100;

        /* soma o de cima e o de baixo */
        double Anv = Anv1+Anv2;
        double Agv = Agv1+Agv2;
        double Ant = Ant1+Ant2;

        double Rd1 = ((0.6 * fu * Anv) + (cts * fu * Ant)) / ya2;

        double Rd2 = ((0.6 * fy * Agv) + (cts * fu * Ant)) / ya2;

        System.out.println(naLinha + " na primeira linha");
        System.out.println(menor.getY());
        System.out.println(maior.getX());
        System.out.println(Anv1);
        System.out.println(Ant1);
        System.out.println(Agv1);

        if (Rd1 < Rd2) {
            System.out.printf("A resistência de cisalhamento em bloco é  %.2f KN \n", Rd1);
            return Rd1;
        } else {
            System.out.printf("A resistência de cisalhamento em bloco é  %.2f KN \n", Rd2);
            return Rd2;
        }

    }


    /* eu add aqui o botao*/
    public  void openResultado (){
        Log.d(TAG, "Acabou!");
        for (int i = 0; i<xyValueArray.size();i++) {
            Log.d(TAG,Double.toString(xyValueArray.get(i).getX()) + ", " + Double.toString(xyValueArray.get(i).getY()));
        }
        Intent intent = new Intent(this, Resultados.class);
        Chapa c = gerarChapa("Chaposa", new Float(bChapa), new Float(tChapa), new Float(hChapa), materialchapaS, "asdasd", true, new Float(dPar), true, new Float(lPar), 0, true);
        ArrayList<String> output = c.ToString();

        Graph graph = gerarGrafo(c);

        Double d = gerarAllPaths(c, graph);
        Log.d("esse e o d " + TAG, Double.toString(d));

        for(int i = 0; i < output.size(); i++){
            Log.d(TAG, output.get(i));
        }

        An1 =AreaLiquida(c.getT(),d);
        Log.d(TAG,"esse e o an " +Double.toString(An1));
        Ag1=AreaBruta(c.getT(),c.getB());
        Log.d(TAG, "esse e o ag " +Double.toString(Ag1));
        RanTra = Resistencia(An1,ya2,fuChapa);
        RagTra = Resistencia(Ag1,ya1,fyChapa);

        /*System.out.printf("A resistência na área líquida é  %.2f KN \n",Ran);
        System.out.printf("A resistência na área bruta é %.2f KN \n",Rag);*/



       ResBloco = ResCisBloco(c, ya2, fuChapa, fyChapa, cts);


        show();
        startActivity(intent);
    } /*ate aqui*/
}
