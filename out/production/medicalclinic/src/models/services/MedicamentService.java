package models.services;

import models.beans.Medicament;
import models.beans.Patient;
import models.dao.MedicamentDao;

import java.util.List;

public class MedicamentService {

    public List<Medicament> getMedicaments() throws ClassNotFoundException {
        MedicamentDao medicamentDao = new MedicamentDao();
        return medicamentDao.selectMedicament(null, 0, null);
    }

    public List<Medicament> getPatientsMedicaments(Patient patient) throws ClassNotFoundException {
        MedicamentDao medicamentDao = new MedicamentDao();
        return medicamentDao.selectMedicament(patient, 0, null);
    }

    public void changeMedicamentAvailability(Medicament medicament, int av) throws ClassNotFoundException {
        MedicamentDao medicamentDao = new MedicamentDao();
        medicamentDao.updateMedicament(medicament, av);
    }
}
