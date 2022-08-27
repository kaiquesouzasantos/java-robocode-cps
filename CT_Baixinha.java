package etec2022;
import robocode.*;
import java.awt.Color;

public class CT_Baixinha extends AdvancedRobot {
	// Variável para controlar o que fazer quando se iniciar a partida
	boolean inicio = true;
	boolean GunD = true;

	public void run() {
		// Seta as cores da equipe:
		setBodyColor(new Color(255, 0, 0));
		setGunColor(new Color(255, 0, 0));
		setRadarColor(new Color(0, 0, 0));

		while(true) {
			// Sempre gira o canhão se não estiver em uma outra função:
			if (GunD == true){
				turnGunRight(180);
			} else {
				turnGunLeft(180);
			}
		}
	}

	// Ao escanear um robô:
	public void onScannedRobot(ScannedRobotEvent e) {
		if (e.getName().indexOf("Border") != -1) {
			return;
		}

		setTurnRight(e.getBearing());
		setAhead(e.getDistance() - 10);

		//  Pega o valor do quanto o canhão deve virar em relação ao seu ângulo atual, usando uma função para normalizar o ângulo e buscar o menor caminho. O cálculo dentro do parênteses pega o 
		// ângulo do inimigo em relação a tela, e adiciona o seu ângulo menos o ângulo do radar.
		double mira = normalRelativeAngle((e.getBearing() + (getHeading() - getRadarHeading())));

		// Dá o comando para o canhão virar em relação ao valor obtido pela função de normalizar ângulos e mira para o inimigo:
		turnGunRight(mira);

		// Atira com potência máxima
		if (e.getDistance() < 80) {
			fire(3);
			GunD = !GunD;
		}
		
	}

	// Ao levar um tiro de um inimigo 
	public void onHitByBullet(HitByBulletEvent e) {}

	// Quando bater em uma parede 
	public void onHitWall(HitWallEvent e) {
		// Quando bater na parede, anda para trás, vira para a esquerda e anda para frente.
		setBack(20);
		setTurnLeft(90);
		setAhead(20);
	}

	// Quando um tiro seu acertar
	public void onBulletHit(BulletHitEvent event) {}

	// Quando a bala se perde (não acerta nenhum robô)
	public void onBulletMissed(BulletMissedEvent event) {}

	// Normalização dos ângulos
	public double normalRelativeAngle(double angle) {
		// Se o ângulo estiver entre -180° e 180° retorna o ângulo, por é preciso normalizar.
		if (angle > -180 && angle <= 180) {
			return angle;
		}

		// Cria uma nova variável para dar retorno com o novo valor.
		double fixedAngle = angle;
	
		// Enquanto menos que -180° adiciona 360° para normalizar o ângulo ao sistema.
		while (fixedAngle <= -180) {
			fixedAngle += 360;
		}

		// Enquanto maior que 180° diminiui 360° para pegar o ângulo equivalente.
		while (fixedAngle > 180) {
			fixedAngle -= 360;
		}

		// Retorna o ângulo obtido.
		return fixedAngle;
	}
		
	// Se a Baixinha bater em um inimigo: 
	public void onHitRobot(HitRobotEvent e) {
		if (e.getName().indexOf("Border") != -1) {
			back(50);
			turnRight(90);
		}
	}
}