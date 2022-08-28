package robocode_cps;
import robocode.*;
import java.awt.Color;

public class CT_FormataMeuPC extends AdvancedRobot {
	boolean inicio = true;
	boolean GunD = true;

	public void run() {
		setBodyColor(Color.black);
		setGunColor(Color.black);
		setRadarColor(new Color(0, 0, 0));

		while(true) {
			// gira o canhão quando inativo
			if (GunD == true){
				turnGunRight(180);
			} else {
				turnGunLeft(180);
			}
		}
	}

	public void onScannedRobot(ScannedRobotEvent elemento) {
		// verifica o tipo de objeto escaneado
		if (elemento.getName().indexOf("Border") != -1) {
			return;
		}

		setTurnRight(elemento.getBearing());
		setAhead(elemento.getDistance() - 10);

		// busca o menor caminho e angulo ideal do inimigo em relaçao a tela, utilizando do metodo normalRelativeAngle
		// calculo: anguloInimigo + (seuAngulo - anguloRadar)
		double mira = ajustaAngulo((elemento.getBearing() + (getHeading() - getRadarHeading())));

		// direciona o angulo de ataque do canhão
		turnGunRight(mira);

		// ajusta o poder de fogo e atira
		if (elemento.getDistance() < 80) {
			// em curtas distancias, o poder de ataque é intenso, pois fica fixado no tanque inimigo. Quase que uma luta corpo á corpo
			fire(7);
			GunD = !GunD;
		} else if(elemento.getDistance() > 80 && elemento.getDistance() < 500){
			// em distancias maiores, o poder de fogo é reduzido
			fire(3);
			GunD = !GunD;
		}
		
	}

	public void onHitWall(HitWallEvent elemento) {
		// quando bater em uma parede: atras -> esquerda -> frente
		setBack(20);
		setTurnLeft(90);
		setAhead(20);
	}

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
		
	public void onHitRobot(HitRobotEvent elemento) {
		// ao entrar em contato com a borda: recua -> esquerda
		if (elemento.getName().indexOf("Border") != -1) {
			back(50);
			turnRight(90);
		}
	}

	// quando sofre um ataque inimigo
	public void onHitByBullet(HitByBulletEvent elemento) {}
	
	// quando o ataque é acertivo
	public void onBulletHit(BulletHitEvent elemento) {}

	// quando o ataque não acerta nenhum inimigo
	public void onBulletMissed(BulletMissedEvent elemento) {}
}
