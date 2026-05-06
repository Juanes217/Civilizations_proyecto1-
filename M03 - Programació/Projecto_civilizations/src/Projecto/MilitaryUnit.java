package Projecto;

public interface MilitaryUnit {
	abstract int attack();
	//Nos devolverá el poder de ataque que tenga la unidad.
	 abstract void takeDamage(int receivedDamage);
	//Restará a nuestro blindaje el daño recibido por parámetro.
	 abstract int getActualArmor();
	//Nos devolverá el blindaje que tengamos actualmente, después de
	//haber recibido un ataque.
	 abstract int getFoodCost();
	//Nos devolverá el coste de Comida que tiene crear una nueva unidad.
	 abstract int getWoodCost();
	//Nos devolverá el coste de Madera que tiene crear una nueva unidad.
	 abstract int getIronCost();
	//Nos devolverá el coste de Hierro que tiene crear una nueva unidad.
	 abstract int getManaCost();
	//Nos devolverá el coste de Mana que tiene crear una nueva unidad.
	 abstract int getChanceGeneratinWaste();
	//Nos la probabilidad de generar residuos al ser totalmente eliminada
	//(blindaje 0 o inferior).
	 abstract int getChanceAttackAgain();
	//Nos la probabilidad de volver a atacar.
	 abstract void resetArmor();
	//Nos restablecerá nuestro blindaje a su valor original.
	 abstract void setExperience(int n);
	//Establecerá la experiencia a un nuevo valor.
	 abstract int getExperience();
	//Nos devolverá la experiencia actual de la unidad.
}
