package juego;


import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	public Entorno entorno;
	// Variables y métodos propios de cada grupo
	// ...
	int aux;
	int aux1;
	int auxRayo;
	int auxFuego;
	Layka layka;
	Manzana[] manzanas;
	int puntaje = 0; // Puntaje del jugador
	int vidas = 3;   // Vidas restantes del jugador
	Planta[] plantas; 
	Rayo[] rayos; 
	Fuego[] fuegos; 
	Auto[] autos;
	int PLANTAS_ELIMINADAS = 0; 
	int MAX_PLANTAS = 4; 
	int MAX_VIDAS = 3;
	int VIDAS=MAX_VIDAS;
	int MAX_AUTOS = 3;
	int MAX_RAYOS = 15;
	int MAX_FUEGOS = 15;
	int[] auxiliar = new int[2];
	Image fondo;

	
	
	public Juego(){
		fondo = Herramientas.cargarImagen("fondo.png");
		auxiliar[0] = -1;
		auxiliar[1] = -1;
		auxRayo = 0;
		plantas = new Planta[MAX_PLANTAS];
		autos = new Auto[MAX_AUTOS];
		rayos = new Rayo[MAX_RAYOS];
		fuegos = new Fuego[MAX_RAYOS];
		layka = new Layka(400,550);
		manzanas = new Manzana[6];
		Herramientas.play("musicaFondo.wav");
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Plantas Invasoras - Grupo ... - v1", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		for (int i=0; i < 6;i++) {
		if(i%2 == 0) {

				manzanas[i]=new Manzana(130*(i+1) ,(175),0.40);
			}
			else {
				manzanas[i]=new Manzana(130*(i) ,(425),0.40);
			}
		}

		// Inicia el juego!
		this.entorno.iniciar();}
	
	public void tick() 
	{
		
		entorno.dibujarImagen(fondo, 400, 285, 0, 1);
		entorno.cambiarFont("Helvetica", 18, Color.white);

		entorno.escribirTexto("VIDAS:" + VIDAS, 25, 25);
		entorno.escribirTexto("PUNTAJE:" + puntaje, 25, 50);
		entorno.escribirTexto("PLANTAS ELIMINADAS:" + PLANTAS_ELIMINADAS, 25, 75);
		
		for (int i = 0; i < MAX_PLANTAS; i++) {
		    switch (i) {
		        case 0:
		            plantas[i] = new Planta(235,550,0.10);
		            break;
		        case 1:
		        	plantas[i] = new Planta(500, 100, 0.10);
		            break;
		        case 2:
		        	plantas[i] = new Planta(25, 350, 0.10);
		            break;
		        case 3:
		        	plantas[i] = new Planta(775, 230, 0.10);
		            break;
		        case 4:
		        	plantas[i] = new Planta(750, 550, 0.10);
		            break;
		    }
		}

for (int i = 0; i < MAX_AUTOS; i++) {
    switch (i) {
        case 0:
            autos[i] = new Auto(600, 525, 0.020);
            break;
        case 1:
            autos[i] = new Auto(250, 75, 0.020);
            break;
        case 2:
            autos[i] = new Auto(50, 325, 0.020);
            break;
        case 3:
            autos[i] = new Auto(600, 550, 0.020);
            break;
        case 4:
            autos[i] = new Auto(750, 550, 0.020);
            break;
    }
}
		
		
		if (VIDAS==0){
			
		}
		
		for (int i=0; i < 6;i++) {
			manzanas[i].dibujar(this.entorno);
	}

		if (entorno.estaPresionada(entorno.TECLA_DERECHA) && colisionManzana(manzanas,layka) != 1) {
			layka.mover(1, this.entorno);

		}
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && colisionManzana(manzanas,layka) != 4) {
			layka.mover(0, this.entorno);

		}	

		if (entorno.estaPresionada(entorno.TECLA_ABAJO)&& colisionManzana(manzanas,layka) != 2) {
			layka.mover(2, this.entorno);

		}

		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)&& colisionManzana(manzanas,layka) != 3 ) {
			layka.mover(3,this.entorno);

		}
		
		if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			Herramientas.play("ladrido.wav");
		    if (auxRayo < MAX_RAYOS) {
		        rayos[auxRayo] = new Rayo((layka.posX() + 5), layka.posY(), 0.08);
		        auxRayo++;
		    }
		}

		if (auxRayo >= MAX_RAYOS) {
		    auxRayo = 0; // Reiniciar auxRayo cuando el arreglo de rayos esté lleno
		}
		
		if (auxFuego >= MAX_FUEGOS) {
		    auxFuego = 0; // Reiniciar auxRayo cuando el arreglo de rayos esté lleno
		}
		
		
		aux=(colisionAutoLayka(autos,layka));
		if (aux !=-1){
			layka = new Layka(400,550);
			Herramientas.play("vidaMenos.wav");
			VIDAS=VIDAS-1;
		}
		
		aux=(colisionPlantaLayka(plantas,layka));
		if (aux !=-1){
			layka = new Layka(400,550);
			Herramientas.play("vidaMenos.wav");
			VIDAS=VIDAS-1;
		}
		
		aux=(colisionFuegoLayka(fuegos,layka));
		if (aux !=-1){
			layka = new Layka(400,550);
			Herramientas.play("vidaMenos.wav");
			fuegos[aux] = null;
			VIDAS=VIDAS-1;
		}
		
		auxiliar=(colisionRayoPlanta(rayos,plantas));
		if (auxiliar[0]!=-1) {
			plantas[auxiliar[0]] = null;
			rayos[auxiliar[1]] = null;
			Herramientas.play("plantaMenos.wav");
			PLANTAS_ELIMINADAS++;
			puntaje=puntaje+5;
		}
		
		auxiliar=(colisionRayoFuego(rayos,fuegos));
		if (auxiliar[0]!=-1) {
			fuegos[auxiliar[0]] = null;
			rayos[auxiliar[1]] = null;
			Herramientas.play("rayo.wav");
		}
		
		
		auxiliar=(colisionFuegoAuto(autos,fuegos));
		if (auxiliar[0]!=-1) {
			autos[auxiliar[0]] = null;
			fuegos[auxiliar[1]] = null;
			Herramientas.play("rayo.wav");
		}
		
		auxiliar=(colisionRayoAuto(autos,rayos));
		if (auxiliar[0]!=-1) {
			rayos[auxiliar[1]] = null;
			Herramientas.play("rayo.wav");
		}
		
		dispararFuego(plantas,layka);
		
		
		layka.dibujar(this.entorno);
				
		for (int i=0; i < autos.length;i++) {
			if(autos[i]!=null) {
			autos[i].dibujar(this.entorno);
			autos[i].mover(this.entorno);
	}}

		for (int i=0; i < plantas.length;i++) {
			if(plantas[i]!=null) {
			plantas[i].dibujar(this.entorno);
			plantas[i].mover(this.entorno);
	}}
		
		for (int i=0; i < rayos.length;i++) {
			if(rayos[i]!=null) {
			rayos[i].dibujar(this.entorno);
			rayos[i].mover(this.entorno);
	}}
		for (int i=0; i < fuegos.length;i++) {
			if(fuegos[i]!=null) {
			fuegos[i].dibujar(this.entorno);
			fuegos[i].mover(this.entorno);
	}}
		
		eliminarFuegosFueraDePantalla(fuegos,this.entorno);
	}



	public int colisionManzana(Manzana[] manzanas, Layka a) {
	    for (int i = 0; i < manzanas.length; i++) {
	        double zona1 = manzanas[i].x - manzanas[i].ancho / 2;
	        double zona2 = manzanas[i].y - manzanas[i].alto / 2;
	        double zona0 = manzanas[i].y + manzanas[i].alto / 2;
	        double zona3 = manzanas[i].x + manzanas[i].ancho / 2;

	        if (a.x > zona1 && a.x < zona3 && a.y > zona2 && a.y < zona0) {
	            // Determina el lado de la colisión
	            if (Math.abs(a.x - zona1) < 5) {
	                return 1; // Colisión en el lado izquierdo
	            } else if (Math.abs(a.x - zona3) < 5) {
	                return 3; // Colisión en el lado derecho
	            } else if (Math.abs(a.y - zona2) < 5) {
	                return 2; // Colisión en la parte superior
	            } else if (Math.abs(a.y - zona0) < 5) {
	                return 4; // Colisión en la parte inferior
	            }
	        }
	    }
	    return 0; // Sin colisión con ninguna manzana
	}
	
	public int colisionAutoLayka(Auto[] autos, Layka a) {
		int i=0;
	    for (Auto auto : autos) {
	    	if (auto != null) { 
	        double zona1 = auto.x - auto.ancho / 2;
	        double zona2 = auto.y - auto.alto / 2;
	        double zona0 = auto.y + auto.alto / 2;
	        double zona3 = auto.x + auto.ancho / 2;

	        if (a.x > zona1 && a.x < zona3 && a.y > zona2 && a.y < zona0) {
	        	return i; // Colisión con al menos un auto desde cualquier dirección
	        }}
	        i++;
	    }
	    return -1; // Sin colisión con ningun auto
	}
	
	
	public int colisionPlantaLayka(Planta[] plantas, Layka a) {
		int i=0;
	    for (Planta planta : plantas) {
	    	 if (planta != null) { 
	        double zona1 = planta.x - planta.ancho / 2;
	        double zona2 = planta.y - planta.alto / 2;
	        double zona0 = planta.y + planta.alto / 2;
	        double zona3 = planta.x + planta.ancho / 2;

	        if (a.x > zona1 && a.x < zona3 && a.y > zona2 && a.y < zona0) {
	        	return i; // Colisión con al menos un auto desde cualquier dirección
	        }}
	        i++;
	    }
	    return -1; // Sin colisión con ningun auto
	}
	
	public int[] colisionRayoPlanta(Rayo[] rayos, Planta[] plantas) {
	    int[] colision = {-1, -1}; // Inicializa el arreglo con -1 en ambas posiciones

	    for (int i = 0; i < rayos.length; i++) {
	        Rayo rayo = rayos[i];
	        if (rayo != null) {
	            for (int j = 0; j < plantas.length; j++) {
	                Planta planta = plantas[j];
	                if (planta != null) {
	                    double zona1 = planta.x - planta.ancho / 2;
	                    double zona2 = planta.y - planta.alto / 2;
	                    double zona0 = planta.y + planta.alto / 2;
	                    double zona3 = planta.x + planta.ancho / 2;

	                    // Verificar colisión entre rayo y planta
	                    if (rayo.x > zona1 && rayo.x < zona3 && rayo.y > zona2 && rayo.y < zona0) {
	                        // Colisión detectada, actualiza el arreglo con las posiciones
	                        colision[0] = j; // Posición de la planta
	                        colision[1] = i; // Posición del rayo
	                        return colision;
	                    }
	                }
	            }
	        }
	    }
	    
	    // No se encontraron colisiones entre rayos y plantas, devuelve el arreglo con -1 en ambas posiciones
	    return colision;
	}
	
	public int[] colisionRayoFuego(Rayo[] rayos, Fuego[] fuegos) {
	    int[] colision = {-1, -1}; // Inicializa el arreglo con -1 en ambas posiciones

	    for (int i = 0; i < rayos.length; i++) {
	        Rayo rayo = rayos[i];
	        if (rayo != null) {
	            for (int j = 0; j < fuegos.length; j++) {
	                Fuego fuego = fuegos[j];
	                if (fuego != null) {
	                    double zona1 = fuego.x - fuego.ancho / 2;
	                    double zona2 = fuego.y - fuego.alto / 2;
	                    double zona0 = fuego.y + fuego.alto / 2;
	                    double zona3 = fuego.x + fuego.ancho / 2;

	                    // Verificar colisión entre rayo y planta
	                    if (rayo.x > zona1 && rayo.x < zona3 && rayo.y > zona2 && rayo.y < zona0) {
	                        // Colisión detectada, actualiza el arreglo con las posiciones
	                        colision[0] = j; // Posición del fuego
	                        colision[1] = i; // Posición del rayo
	                        return colision;
	                    }
	                }
	            }
	        }
	    }
	    
	    // No se encontraron colisiones entre rayos y plantas, devuelve el arreglo con -1 en ambas posiciones
	    return colision;
	}
	
	public int[] colisionFuegoAuto(Auto[] autos, Fuego[] fuegos) {
	    int[] colision = {-1, -1}; // Inicializa el arreglo con -1 en ambas posiciones

	    for (int i = 0; i < autos.length; i++) {
	    	Auto auto = autos[i];
	        if (auto != null) {
	            for (int j = 0; j < fuegos.length; j++) {
	                Fuego fuego = fuegos[j];
	                if (fuego != null) {
	                    double zona1 = fuego.x - fuego.ancho / 2;
	                    double zona2 = fuego.y - fuego.alto / 2;
	                    double zona0 = fuego.y + fuego.alto / 2;
	                    double zona3 = fuego.x + fuego.ancho / 2;

	                    // Verificar colisión entre rayo y planta
	                    if (auto.x > zona1 && auto.x < zona3 && auto.y > zona2 && auto.y < zona0) {
	                        // Colisión detectada, actualiza el arreglo con las posiciones
	                        colision[0] = i; // Posición del fuego
	                        colision[1] = j; // Posición del rayo
	                        return colision;
	                    }
	                }
	            }
	        }
	    }
	    
	    // No se encontraron colisiones entre rayos y plantas, devuelve el arreglo con -1 en ambas posiciones
	    return colision;
	}
	
	public int[] colisionRayoAuto(Auto[] autos, Rayo[] rayos) {
	    int[] colision = {-1, -1}; // Inicializa el arreglo con -1 en ambas posiciones

	    for (int i = 0; i < autos.length; i++) {
	    	Auto auto = autos[i];
	        if (auto != null) {
	            for (int j = 0; j < rayos.length; j++) {
	                Rayo rayo = rayos[j];
	                if (rayo != null) {
	                    double zona1 = rayo.x - rayo.ancho / 2;
	                    double zona2 = rayo.y - rayo.alto / 2;
	                    double zona0 = rayo.y + rayo.alto / 2;
	                    double zona3 = rayo.x + rayo.ancho / 2;

	                    // Verificar colisión entre rayo y planta
	                    if (auto.x > zona1 && auto.x < zona3 && auto.y > zona2 && auto.y < zona0) {
	                        // Colisión detectada, actualiza el arreglo con las posiciones
	                        colision[0] = j; // Posición del fuego
	                        colision[1] = j; // Posición del rayo
	                        return colision;
	                    }
	                }
	            }
	        }
	    }
	    
	    // No se encontraron colisiones entre rayos y plantas, devuelve el arreglo con -1 en ambas posiciones
	    return colision;
	}
	
	public int colisionFuegoLayka(Fuego[] fuegos, Layka a) {
		int i=0;
	    for (Fuego fuego : fuegos) 
	    { if (fuego != null) { 
	        double zona1 = fuego.x - fuego.ancho / 2;
	        double zona2 = fuego.y - fuego.alto / 2;
	        double zona0 = fuego.y + fuego.alto / 2;
	        double zona3 = fuego.x + fuego.ancho / 2;

	        if (a.x > zona1 && a.x < zona3 && a.y > zona2 && a.y < zona0) {
	        	return i; // Colisión con al menos un auto desde cualquier dirección
	        }}
	        i++;
	    }
	    return -1; // Sin colisión con ningun auto
	}
	
	private long tiempoUltimoDisparo = 0; // Variable para rastrear el tiempo del último disparo
	private final long tiempoEntreDisparos = 1000; // Tiempo en milisegundos entre disparos (1 segundo)

	public void dispararFuego(Planta[] objetosPlanta, Layka layka) {
	    long tiempoActual = System.currentTimeMillis(); // Obtiene el tiempo actual en milisegundos

	    for (Planta planta : objetosPlanta) {
	        if (planta != null) {
	            // Comprueba si la planta está alineada con Layka en el eje X o Y
	            if (Math.abs(planta.x - layka.x) < 5 || Math.abs(planta.y - layka.y) < 5) {
	                // Comprueba si ha transcurrido al menos 1 segundo desde el último disparo
	                if (tiempoActual - tiempoUltimoDisparo >= tiempoEntreDisparos) {
	                    double angulo = Math.atan2(layka.y - planta.y, layka.x - planta.x); // Ajusta la velocidad del fuego según tus necesidades

	                    // Calcula la posición inicial del fuego
	                    double xInicialFuego = planta.x;
	                    double yInicialFuego = planta.y;

	                    // Crea el objeto Fuego con las coordenadas iniciales y dirección hacia Layka
	                    Fuego fuego = new Fuego(xInicialFuego, yInicialFuego, 0.05, angulo);
	                    // Agrega el objeto Fuego al arreglo de fuegos
	                    for (int i = 0; i < fuegos.length; i++) {
	                        if (fuegos[i] == null) {
	                            fuegos[i] = fuego;
	                            tiempoUltimoDisparo = tiempoActual; // Actualiza el tiempo del último disparo
	                            break;
	                        }
	                    }
	                }
	            }
	        }
	    }
	}
	public void eliminarFuegosFueraDePantalla(Fuego[] fuegos, Entorno entorno) {
	    for (int i = 0; i < fuegos.length; i++) {
	        if (fuegos[i] != null) {
	            double x = fuegos[i].x;
	            double y = fuegos[i].y;
	            // Comprueba si el fuego ha salido de la pantalla
	            if (x < -50.0 || x > entorno.ancho() + 50.0 || y < -50.0 || y > entorno.alto() + 50.0) {
	                fuegos[i] = null; // Establece el valor del fuego a null
	            }
	        }
	    }
	}



	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}}

