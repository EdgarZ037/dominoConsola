package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class MainCliente {
	public static int tt=0;
	public static String textoPrincipal= "Para comenzar escriba 'jugar' para tomar sus fichas";
	public static String Resultado= "Sus fichas son: ";
	public static String actualizador="";
	public static int[][] players = new int[2][10];
	public static int jug=0;
	public static int numT=4;
	public static int numF=9;
	public static int puntos=0;
	public static int fin=0;
	public static Boolean pasado=false;
	public static String Table="";
    private final static int PORT = 5000;
    private static String SERVER = "";
    
    public static void main(String[] args) {
    	boolean ip=false;
    	String dirIp="";
    	System.out.println("Bienvenido! introduzca la ip del servidor jugar");
    	Scanner dir = new Scanner(System.in);
    	
    	do{
    		dirIp = dir.nextLine();
    		if(dirIp.equals("localhost") || dirIp.length()>11){
    			SERVER=dirIp;
    			ip=true;
    		}
    		
    	}while(!ip);
    	
    	boolean exit=false;//bandera para controlar ciclo del programa
        Socket socket;      
        
        try {            
            System.out.println("Checando Conexion...");  
            
            while( !exit ){    
            	
                socket = new Socket(SERVER, PORT);  
                BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream())); 
                PrintStream output = new PrintStream(socket.getOutputStream());                
                BufferedReader brRequest = new BufferedReader(new InputStreamReader(System.in));            
                System.out.println(textoPrincipal); 
                Boolean full=false;
                String request;
                
                do{
                	request = brRequest.readLine();
                	if(request.equals("")){
                		System.out.println("Comando incorrecto");
                	}
                	else{ 
                		String p=request.substring(0,1);
                		if(request.equals("jugar")||request.equals("ver")||p.equals("s")||p.equals("n")){
                			if((p.equals("v")||p.equals("s")) && jug!=numT){
                        		System.out.println("Aun no es tu turno presiona 2");
                        	}else if(p.equals("n")||p.equals("v")){
                				actualizador="";
                				full=true;
                			}else if(p.equals("s")&&request.length()>1){
                    			System.out.println("Introduzca solo 's'");
                    		}else{
                    			full=true;
                    		}
                		}else if(request.equals("finalizar") && (numF==0 || fin==1 ) ){
                			full=true;
                			
                		}else{
                			System.out.println("comando no valido");
                		}
                	}
                }while(!full);
                
                output.println(request+actualizador); 
                String st = input.readLine();
                
                if(request.equals("finalizar")){
                	
                	String ng= st.substring(st.length()-1,st.length());
                	System.out.println(ng);
                	
                	if(ng=="1"){
                		Resultado="No hay ganador Fin del juego";
                	}else{
                		Resultado="Fin de juego Ganador jugador: ";
                	}
                	exit=true;                  
                    System.out.println("Gracias por jugar");
                }
                
                if( st != null ){
                	if((st.substring(0,1).equals("0")||st.substring(0,1).equals("1")) && !request.equals("finalizar")){
                		String ttt=st.substring(2,st.length());
                		System.out.println(Resultado +" "+ ttt );
                	}else{
                		System.out.println(Resultado + st );
                	}
                	
                }
                
                if(request.equals("jugar")){//Reparte las fichas
                    
                    jug = Integer.parseInt( st.substring( 0,1) );
                    numT = Integer.parseInt( st.substring( 1,2) );
                    Resultado ="Tablero";
                    
                    
                    System.out.println("Usted es el Jugador "+jug);
                    int d=0;
                    
                    for(int r=8; r>-1;r--){
	                    int numD = Integer.parseInt( st.substring( (d+st.length()-2),(d+st.length()-1) ) );
	                    int numI = Integer.parseInt(st.substring((d+st.length()-4),(d+st.length()-3)));
	                    
	                    players[0][r]=numI;players[1][r]=numD;
	                    puntos += (numI+numD);
	                    d-=5;
                    
                    }
                    /*for(int j=0;j<9;j++){
                    	System.out.println("Ficha"+(j+1)+"["+players[0][j]+"|"+players[1][j]+"]");
                    }*/
                    
                        
                } 
                String compro=request.substring(0,1);
                
                if(compro.equals("s")){
                	textoPrincipal = "Turno exitoso Presione 2 para continuar";
                	numT= Integer.parseInt( st.substring( 1,2) );
                	fin= Integer.parseInt( st.substring( 0,1) );
                	//System.out.println("fin "+fin);
                	//Table=st.substring( 2,st.length());
                	
                }
                //System.out.println(Table);
                if(compro.equals("n")){
                	if(fin==0){
                		textoPrincipal = "Espere su turno introduzca 'n' para continuar";
                	}else{
                		textoPrincipal = "Juego Terminado Escriba finalizar";
                	}
                	numT= Integer.parseInt( st.substring( 1,2) );
                	fin= Integer.parseInt( st.substring( 0,1) );
                	
                }
                
                if(jug==numT && fin==0){
                		
                		textoPrincipal="Introduzca 'ver' para checar el tablero actual";

                }else{
                	
                	if(fin==0){
                		textoPrincipal="Espere su turno intoduzca 'n' para continuar Turno actual jugador"+numT;
                	}else{
                		
                	}
                }
                
                Scanner sc = new Scanner(System.in);
                String comando; 
                int mi;
                int compD;
                int compI;
                int fd;
                int fi;
                String tab="";
                
                if(request.equals("ver")){
                	
                	fin= Integer.parseInt( st.substring( 0,1) );
                	System.out.println(fin);
                	
                	if(fin==0){
	                	textoPrincipal = "Ficha valida! introduzca 's' para continuar.";
	                	
	                	System.out.println("Numero de fichas restantes: "+numF);
	                	System.out.println("Puntos actuales: "+puntos);
	                	
	                	for(int j=0;j<9;j++){
	                		if(players[0][j]!=7){
	                			System.out.println((j+1)+". ["+players[0][j]+"|"+players[1][j]+"]");
	                			
	                		}	
	                    }
	                     System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4') \n+"
	                     		+ "'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                     comando = sc.nextLine();
	                     Boolean vac=false;
	                     do{
	                    	 if(comando.equals("")||comando.length()>3){
	                    		 vac=false;
	                    		 System.out.println("comando invalido");
	                    		 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4') \n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                             comando = sc.nextLine();
	                             
	                    	 }else{
	                    		 vac=true;
	                    	 }
	                    	 
	                     }while(!vac);
	                     
	                     Boolean sal=false;
	                     Boolean numV=false;
	                     Boolean val=false;
	                     String lado=comando.substring(0,1);
	                     int medir;
	                     medir = comando.length();
	                     tab=st.substring(2,st.length());
	                     
	                     do{
	                    	 
	                    	 if(lado.equals("d") || lado.equals("i") || lado.equals("p") ){
	                    		 if(lado.equals("p")){
	                    			 val=true; 
	                    		 }
	                    		 
	                    		 if(lado.equals("d")|| lado.equals("i")){
	                    			 if(medir==3){
	                    				 String n=comando.substring(2,3);
	                    				 if(n.equals("1")||n.equals("2")||n.equals("3")||n.equals("4")||n.equals("5")||n.equals("6")||n.equals("7")||n.equals("8")||n.equals("9")){
	                    					 val=true;
	                    				 }else{
	                    					 val=false;
	                                		 System.out.println("comando invalido");
	                                		 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4') "
	                                		 		+ "\n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                                         comando = sc.nextLine();
	                                         Boolean test=false;
	                                         
	                                         do{
	                                        	 if(comando.equals("")){
	                                        		 test=false;
	                                        		 System.out.println("comando invalido");
	                                        		 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4') \n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                                                 comando = sc.nextLine();
	                                                 
	                                        	 }else{
	                                        		 test=true;
	                                        	 }
	                                         }while(!test);
	                                         
	                                         lado=comando.substring(0,1);
	                                         medir = comando.length();
	                    				 }
	                    				 
	                    			 }else{
	                    				 val=false;
	                            		 System.out.println("comando invalido");
	                            		 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4') "
	                            		 		+ "\n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                                     comando = sc.nextLine();
	                                     Boolean test=false;
	                                     
	                                     do{
	                                    	 if(comando.equals("") ){
	                                    		 test=false;
	                                    		 System.out.println("comando invalido");
	                                    		 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4')"
	                                    		 		+ " \n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                                             comando = sc.nextLine();
	                                             
	                                    	 }else{
	                                    		 test=true;
	                                    	 }
	                                    	 
	                                     }while(!test);
	                                     
	                                     lado=comando.substring(0,1);
	                                     medir = comando.length();
	                    			 }
	                    		 }
	                    		 
	                    	 }else{
	                    		 
	                    		 val=false;
	                    		 System.out.println("comando invalido");
	                    		 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4') "
	                    		 		+ "\n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                             comando = sc.nextLine();
	                             Boolean test=false;
	                             
	                             do{
	                            	 if(comando.equals("")){
	                            		 test=false;
	                            		 System.out.println("comando invalido");
	                            		 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4') "
	                            		 		+ "\n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                                     comando = sc.nextLine();
	                                     
	                            	 }else{
	                            		 test=true;
	                            	 }
	                             }while(!test);
	                             
	                             lado=comando.substring(0,1);
	                             medir = comando.length();
	                    		 
	                    	 }
	                     }while(!val);
	                     
	                     if(lado.equals("p")){
	                    	 System.out.println("Pasaste de turno");
	                    	 
	                    	 actualizador="paso";
	                		 //System.out.println(actualizador);
	                		 sal=true;
	                     }else{
	                    	 mi = Integer.parseInt( comando.substring( (comando.length()-1),(comando.length()) ) );
	                    	 
	                         do{
	                        	 
	                        	 if(mi>0 && mi<10){
	                        		 numV=true;
	                        	 }else{
	                        		 System.out.println("Introduce una ficha valida ejem 'izq 1' o pasa 'pas' ");
	                        		 comando = sc.nextLine();
	                        		 lado=comando.substring(0,1);
	                            	 mi = Integer.parseInt( comando.substring( (comando.length()-1),(comando.length()) ) );
	                                 compD= players[1][mi-1];
	                                 compI= players[0][mi-1];
	                        	 }
	                        	
	                         }while(!numV);
	                         
	                         compD= players[1][mi-1];
	                         compI= players[0][mi-1];
	                         fd = Integer.parseInt( st.substring( (st.length()-2),(st.length()-1) ) );
	                         fi = Integer.parseInt(st.substring(3,4));
	                         
	                         
	                         do{
	                        	 if(lado.equals("d")){
	                        		 if(compD==fd){
	                        			 actualizador=tab+"["+compD+"|"+compI+"]";
	                        			 puntos=puntos-(players[0][mi-1]+players[1][mi-1]);
	                        			 players[1][mi-1]=7;
	                                	 players[0][mi-1]=7;
	                                	 numF--;
	                                	 
	                        			 sal=true;
	                        		 }else if(compI==fd){
	                        			 actualizador=tab+"["+compI+"|"+compD+"]";
	                        			 puntos=puntos-(players[0][mi-1]+players[1][mi-1]);
	                        			 players[1][mi-1]=7;
	                                	 players[0][mi-1]=7;
	                                	 
	                                	 numF--;
	                        			 sal=true;
	                        		 }else if(lado.equals("p")){
	                                	 System.out.println("Pasate de turno");
	                                	 
	                                	 actualizador="paso";
	                            		 //System.out.println(actualizador);
	                            		 sal=true;
	                                 }else{
	                        			 System.out.println("Ficha no valida");
	                        			 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4') "
	                        			 		+ "\n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                            		 comando = sc.nextLine();
	                            		 Boolean test=false;
	                            		 
	                                     do{
	                                    	 if(comando.equals("")){
	                                    		 test=false;
	                                    		 System.out.println("comando invalido");
	                                    		 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4') "
	                                    		 		+ "\n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                                             comando = sc.nextLine();
	                                             
	                                    	 }else{
	                                    		 test=true;
	                                    	 }
	                                    	 
	                                     }while(!test);
	                                     
	                            		 lado= comando.substring(0,1);
	                            		 
	                            		 if(lado.equals("p")){
	                            			 System.out.println("Pasate de turno");
	                                    	 
	                                    	 actualizador="paso";
	                                		 //System.out.println(actualizador);
	                                		 sal=true;
	                            		 }else{
	                            			 if(comando.length()==3){
	                            				 String n=comando.substring(2,3);
	                            				 if(n.equals("1")||n.equals("2")||n.equals("3")||n.equals("4")||n.equals("5")||n.equals("6")||
	                            					n.equals("7")||n.equals("8")||n.equals("9")){
	                            					 mi = Integer.parseInt( comando.substring( (comando.length()-1),(comando.length()) ) );
	                            				 }else{
	                            					 System.out.println("Comando no valido");
	                            				 }
	                            				 
	                            			 }else{
	                            				 System.out.println("Comando no valido");
	                            			 }
	                            		 }
	                                     compD= players[1][mi-1];
	                                     compI= players[0][mi-1];
	                        		 }
	                        	 }else if(lado.equals("i")){
	                        		 if(compD==fi){
	                        			 actualizador="["+compI+"|"+compD+"]"+tab;
	                        			 puntos=puntos-(players[0][mi-1]+players[1][mi-1]);
	                        			 players[1][mi-1]=7;
	                                	 players[0][mi-1]=7;
	                                	 
	                                	 numF--;
	                        			 sal=true;
	                        		 }else if(compI==fi){
	                        			 actualizador="["+compD+"|"+compI+"]"+tab;
	                        			 puntos=puntos-(players[0][mi-1]+players[1][mi-1]);
	                        			 players[1][mi-1]=7;
	                                	 players[0][mi-1]=7;
	                                	 
	                                	 numF--;
	                        			 sal=true;
	                        		 }else if(lado.equals("p")){
	                                	 System.out.println("Pasate de turno");
	                                	 
	                                	 actualizador="paso";
	                            		 //System.out.println(actualizador);
	                            		 sal=true;
	                                 }else{
	                        			 System.out.println("Ficha no valida");
	                        			 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4') "
	                        			 		+ "\n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                            		 comando = sc.nextLine();
	                            		 Boolean test=false;
	                            		 
	                                     do{
	                                    	 if(comando.equals("")){
	                                    		 test=false;
	                                    		 System.out.println("comando invalido");
	                                    		 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4')"
	                                    		 		+ "\n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                                             comando = sc.nextLine();
	                                             
	                                    	 }else{
	                                    		 test=true;
	                                    	 }
	                                    	 
	                                     }while(!test);
	                                     
	                            		 lado= comando.substring(0,1);
	                            		 
	                            		 if(lado.equals("p")){
	                            			 System.out.println("Pasate de turno");
	                                    	 
	                                    	 actualizador="paso";
	                                		 //System.out.println(actualizador);
	                                		 sal=true;
	                            		 }else{
	                            			 if(comando.length()==3){
	                            				 String n=comando.substring(2,3);
	                            				 if(n.equals("1")||n.equals("2")||n.equals("3")||n.equals("4")||n.equals("5")||n.equals("6")||n.equals("7")||n.equals("8")||n.equals("9")){
	                            					 mi = Integer.parseInt( comando.substring( (comando.length()-1),(comando.length()) ) );
	                            				 }else{
	                            					 System.out.println("Comando no valido");
	                            				 }
	                            			 }else{
	                            				 System.out.println("Comando no valido");
	                            			 } 
	                            		 }
	                                	 
	                                     compD= players[1][mi-1];
	                                     compI= players[0][mi-1];
	                        			 
	                        		 }
	                        	 }else{
	                        		 //System.out.println("Entre en el else");
	                        		 comando = sc.nextLine();
	                        		 Boolean test=false;
	                        		 
	                                 do{
	                                	 if(comando.equals("")){
	                                		 test=false;
	                                		 System.out.println("comando invalido");
	                                		 System.out.println("Menu introduce: \n'd' y el num de ficha (lado derecho del tablero ejemplo 'd 4') "
	                                		 		+ "\n'i' y el num de ficha (lado izquierdo del tablero ejemplo 'd 7') \n'p' (pasar de turno)");
	                                         comando = sc.nextLine();
	                                         
	                                	 }else{
	                                		 test=true;
	                                	 }
	                                 }while(!test);
	                                 
	                        		 lado=comando.substring(0,1);
	                        	 }
	                         }while(!sal);
	                         
	                     }
                	}else{
                		System.out.println("Juego finalizado puntos"+puntos);
                		textoPrincipal="Escriba finalizar para terminar";
                	}  
                	
                }
                
                if(request.equals("exit")){//terminar aplicacion
                    exit=true;                  
                    System.out.println("Cliente> Fin de programa");    
                }  
                if(numF==0){
                	textoPrincipal="Ganaste! Escribe finalizar";
                	actualizador+=""+"G";
                }
                
                if(fin==1){
                	textoPrincipal="Juego Terminado Escribe finalizar";
                	actualizador=""+puntos;
                	System.out.println("Puntos "+puntos);
                }
                
                socket.close();
                
            }//end while                                    
       } catch (IOException ex) {        
         System.err.println("Jugador 1> " + ex.getMessage());   
       }
    }
    
    
}
