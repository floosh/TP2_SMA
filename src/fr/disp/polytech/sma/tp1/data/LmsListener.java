package fr.disp.polytech.sma.tp1.data;

import java.util.ArrayList;
import java.util.EventListener;

/** Cette interface est un listerer qui permet la mise a jour des trame de telemetre laser
 * 
 * auteur B dafflon pour le laboratoire Systemes et Transport UTBM
 */

public interface LmsListener extends EventListener{
	void setData(ArrayList<Double> values2);
}
