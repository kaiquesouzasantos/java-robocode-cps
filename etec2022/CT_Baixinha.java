package etec_ct_2022;
import robocode.*;
import java.awt.Color;

public class CT_Baixinha extends AdvancedRobot {
	boolean inicio = true;
	boolean GunD = true;

	public void run() {
		setBodyColor(new Color(255, 0, 0));
		setGunColor(new Color(255, 0, 0));
		setRadarColor(new Color(0, 0, 0));

		while(true) {
			if (GunD == true){
				turnGunRight(180);
			} else {
				turnGunLeft(180);
			}
		}
	}

	public void onScannedRobot(ScannedRobotEvent evento) {
		if (evento.getName().indexOf("Border") != -1) {
			return;
		}

		setTurnRight(evento.getBearing());
		setAhead(evento.getDistance() - 10);

		double mira = normalRelativeAngle((evento.getBearing() + (getHeading() - getRadarHeading())));

		turnGunRight(mira);

		if (evento.getDistance() < 80) {
			fire(3);
			GunD = !GunD;
		}
		
	}

	public void onHitByBullet(HitByBulletEvent evento) {}

	public void onHitWall(HitWallEvent evento) {
		setBack(20);
		setTurnLeft(90);
		setAhead(20);
	}

	public void onBulletHit(BulletHitEvent evento) {}

	public void onBulletMissed(BulletMissedEvent evento) {}

	public double normalRelativeAngle(double angulo) {
		if (angulo > -180 && angulo <= 180) {
			return angulo;
		}

		double anguloFixado = angulo;
	
		while (anguloFixado <= -180) {
			anguloFixado += 360;
		}

		while (anguloFixado > 180) {
			anguloFixado -= 360;
		}

		return anguloFixado;
	}
		
	public void onHitRobot(HitRobotEvent evento) {
		if (evento.getName().indexOf("Border") != -1) {
			back(50);
			turnRight(90);
		}
	}
}
