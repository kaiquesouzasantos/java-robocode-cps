import robocode.*;
import java.awt.Color;

/*
	- Etec: 199 - Etec Cidade Tiradentes
	- Orientador: Danadoni Lima dos Santos
 	- Robo: Adestrador De Samurai
  	- Curso: Desenvolvimentos de Sistemas
  	- Integrante: Kaique Souza Santos
*/

public class AdestradorDeSamurai extends AdvancedRobot {
	// variaveis de inicialização
	public boolean inicio = true, canhao = true;

	public void run() {
		// definições esteticas
		setBodyColor(Color.black);
		setGunColor(Color.black);
		setRadarColor(new Color(0, 0, 0));

		while(true) {
			// gira o canhão quando não estiver executando outras funções
			if (canhao == true){
				turnGunRight(180);
			} else {
				turnGunLeft(180);
			}
		}
	}

	public void onScannedRobot(ScannedRobotEvent elemento) {
		// verifica o tipo de objeto escaneado, desconsideranco-o se for uma parede
		if (elemento.getName().indexOf("Border") != -1) {
			return;
		}

		// robo: direita(anguloInimigo) -> avança(distanciaInimigo - 10)
		setTurnRight(elemento.getBearing());
		setAhead(elemento.getDistance() - 10);

		// busca o menor caminho e angulo ideal do inimigo em relaçao a tela, utilizando do metodo ajustaAngulo
		// calculo: anguloInimigo + (seuAngulo - anguloRadar)
		double mira = ajustaAngulo((elemento.getBearing() + (getHeading() - getRadarHeading())));

		// direciona o angulo de ataque do canhão
		turnGunRight(mira);

		// ajusta o poder de fogo e atira
		if (elemento.getDistance() < 80) {
			// em curtas distancias, o poder de ataque é intenso, pois fica fixado no tanque inimigo. Quase que uma luta corpo á corpo
			fire(7);
			// para a rotação do canhao
			canhao = !canhao;
		} else if(elemento.getDistance() > 80 && elemento.getDistance() < 500){
			// em distancias maiores, o poder de fogo é reduzido
			fire(3);
			// para a rotação do canhao
			canhao = !canhao;
		}
		
	}

	public void onHitWall(HitWallEvent evento) {
		// quando o robo colide com uma parede: recua -> esquerda -> avanca
		setBack(20);
		setTurnLeft(90);
		setAhead(20);
	}

	// basicamente: ajusta o angulo da linha de fogo, para obter uma maior acertividade. 
	public double ajustaAngulo(double angulo) {
		// se o angulo estiver entre -180° e 180, ele não necessita de ajuste
		if (angulo > -180 && angulo <= 180) {
			return angulo;
		}

		double anguloFixado = angulo;
	
		// equanto for <= -180°: adiciona 360°
		while (anguloFixado <= -180) {
			anguloFixado += 360;
		}

		// enquanto for > 180: subtrai 360°
		while (anguloFixado > 180) {
			anguloFixado -= 360;
		}

		return anguloFixado;
	}
		
	// quando colide com um inimigo
	public void onHitRobot(HitRobotEvent evento) {}

	// quando sofre um ataque inimigo
	public void onHitByBullet(HitByBulletEvent evento) {}
	
	// quando o ataque é acertivo
	public void onBulletHit(BulletHitEvent evento) {}

	// quando o ataque colide com a parede/borda
	public void onBulletMissed(BulletMissedEvent evento) {}
}
