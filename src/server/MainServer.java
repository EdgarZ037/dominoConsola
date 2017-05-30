package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Random;

public class MainServer {

    private final static int PORT = 5000;
    public static int[] conteo = new int[4];
    public static int[] puntos = new int[4];
    public static String[] mula = new String[4];
    public static int conta=0;
    
    
    public static int[][] players = new int[6][10];
    public static String[] puntoss = new String[4];
    public static String fichaPrincipal = "";
    public static String tablero = "";
    public static int Turno = 1;
    public static int T = 1;
    public static int CTurno = 1;
    public static int paso=0;
    public static int fin=0;

    public static int[] asig = new int[29];
    public static String[] phrases = {
            "",
            "[0|0]",
            "[0|1]",
            "[0|2]",
            "[0|3]",
            "[0|4]",
            "[0|5]",
            "[0|6]",
            "[1|1]",
            "[1|2]",
            "[1|3]",
            "[1|4]",
            "[1|5]",
            "[1|6]",
            "[2|2]",
            "[2|3]",
            "[2|4]",
            "[2|5]",
            "[2|6]",
            "[3|3]",
            "[3|4]",
            "[3|5]",
            "[3|6]",
            "[4|4]",
            "[4|5]",
            "[4|6]",
            "[5|5]",
            "[5|6]",
            "[6|6]"};
    
    public static String[] asignadas = new String[28];
    public static String trampa = "";
    public static int Contador=0;
    public static int mas=0;
    public static int ganador=0;
    public static int jg=0;
    
    
    public static void main(String[] args) {
        
        try {
            @SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(PORT);
            
            System.out.println("Servidor encendido"); 
            
            System.out.println("Esperando jugadores...");   
            Socket clientSocket;
                while(true){
                    clientSocket = serverSocket.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintStream output = new PrintStream(clientSocket.getOutputStream());
                    String request = input.readLine();
                    System.out.println("Jugador "+T+"> pide '" + request +  "'");
                    String compro=request.substring(0,1);
                    //String tab=request.substring(0,5);
                    
                    if(compro.equals("f")){
                        
                        fin=1;
                        if(ganador==0 ){
                            String GG=request.substring(request.length()-1,request.length());
                            if(GG.equals("G")){
                                System.out.println("Ganador jugador "+T);
                                jg=T;
                                tablero=request.substring(9,request.length()-1);
                                trampa="Bien jugado!";
                            }else{
                                
                            puntoss[T]=request.substring(9,request.length());
                            trampa=""+fin+""+CTurno+""+jg;
                        
                            System.out.println("Puntos jugador "+T+" "+puntoss[T]);
                            } 
                        }else{
                        	
                            puntoss[T]=request.substring(9,request.length());
                            trampa=puntoss[T];
                            System.out.println("Puntos jugador "+T+" "+puntoss[T]);
                        }
                    }
                    
                    if(compro.equals("s")){
                        
                        if(request.substring(1,2).equals("p")){
                            paso++;
                            System.out.println("Jugador "+T+" pasa numero de pasadas"+paso);
                            trampa=""+fin+""+CTurno+""+tablero;
                            if(paso==3){
                                fin=1;
                                System.out.println("El juego Termina en tablas");
                                trampa=""+fin+""+CTurno+""+tablero;
                            }
                        }else{
                            paso=0;
                            System.out.println("Turno de jugador "+T);
	                        
	                        tablero=request.substring(1,request.length());
	                        System.out.println("Tablero actualizado!: "+tablero);
	                        trampa=fin+""+CTurno+""+tablero;
                        }

                    }
                    
                    if(compro.equals("n")){
                        if(fin==1){
                            if(jg!=0){
                                T++;
                                if(T>3){
                                    T=1;
                                 } 
                                trampa=fin+""+CTurno+""+tablero+"Juego finalizado. Gano el jugador "+jg;
                                
                            }else{
                                trampa=fin+""+CTurno+""+tablero+"Juego finalizado en tablas";
                            }
                            
                        }else{
                            trampa=fin+""+CTurno+""+tablero;
                        }
                        
                    }else if(compro.equals("v") ){
                        if(fin==1){
                            if(jg!=0){
                                trampa=fin+""+CTurno+""+tablero+"Juego finalizado. Gano el jugador"+jg;
                                
                            }else{
                                trampa=fin+""+CTurno+""+tablero+"Juego finalizado en tablas";
                            }
                            CTurno++;
                        
                            if(CTurno>3){
                                CTurno=1;                          
                            }
                        }else{
                            CTurno++;
                        
                            if(CTurno>3){
                                CTurno=1;                          
                            }
                                 process(request);
                        }
                        
                    }else{
                        process(request); 
                    }
                     
                    output.flush();//vacia contenido
                    if(request.equals("jugar")){
                        
                        trampa = Turno+""+CTurno+""+trampa;
                        
                    }
                    T++;
                    
                    if(T>3){
                        T=1;
                    } 
                    
                    output.println(trampa);  
                    Turno++;
                    trampa="";
                    conta++;
                    //cierra conexion
                    clientSocket.close();

                }  
                        
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
    public static String process(String request){
        String result="";   
        if(request!=null) 
            switch(request){
                
            case "jugar":
                
                if(Contador!=0){
                    
                    mas +=9;
                }
                   for(int i=0;i<9;i++){
                       Random azar= new Random();
                       int arr;
                       arr = (int)(azar.nextDouble() * 29 + 0);
                
                       Boolean pass= false;
                
                do{
                    
                    for(int r=0; r<29;r++){
                        
                        if(arr==asig[r]){
                            arr = (int)(azar.nextDouble() * 29 + 0);
                            r=-1; 
                        }
                        pass=true;
                    }
                    
                }while(pass==false);
                
                result = phrases[arr];
                phrases[arr]="";
                
                asig[i+mas]= arr;
                asignadas[i+mas] = result;
                int numI = Integer.parseInt(result.substring(1,2));
                int numD = Integer.parseInt(result.substring(3,4));
                
                switch(mas){
                    case 0: players[0][i]=numI;players[1][i]=numD;break;
                    case 9: players[2][i]=numI;players[3][i]=numD;break;
                    case 18: players[4][i]=numI;players[5][i]=numD;break;
                }
                
                	trampa += ""+ result+"";
                
                   } 
                Contador++;
                
               System.out.println("Player"+Contador +"Fichas: "+trampa);

               //System.out.println(restantes);
               for(int j=0;j<29;j++){
                   if(phrases[j] != ""){
                       tablero = ""+phrases[j]+"";
                       break;
                       
                   }
                   // System.out.println("Fichas Asignadas"+asig[j]);
                }
               
               switch(mas){
                    case 0: System.out.println("Falta el jugador 2 y 3");
                    //for(int j=0;j<9;j++){System.out.println("Fichas "+j+" ["+players[0][j]+"|"+players[1][j]+"]" );
                    //}
                    break;
                    case 9: System.out.println("Falta el jugador 3");
                       
                    break;
                    case 18: System.out.println("Juguemos!");
                    //jugar();
                    //for(int j=0;j<9;j++){System.out.println("Fichas "+j+" ["+players[4][j]+"|"+players[5][j]+"]" );
                    //}   
                    break;
                }
                break;//break del case jugar
            case "ver":
                trampa=fin+""+CTurno+""+tablero;
                
                break;
            case "exit":             
                
                result = "bye";
                break;
            default:
                result = "La peticion no se puede resolver.";
                break;
        }
        return result;
    }
 
    
}