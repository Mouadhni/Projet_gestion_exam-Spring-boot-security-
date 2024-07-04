import React, { useState, useEffect } from 'react';
import DefaultLayout from '../../../layout/DefaultLayout';
import Breadcrumb from '../../../components/Breadcrumbs/Breadcrumb';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import MultiSelect from '../MultiSelect';
import MultiSelectModule from '../MultiSelectModule';

const Exam_salle_surv = () => {
  const [date, setDate] = useState('');
  const [selectedEnseignants, setSelectedEnseignants] = useState<number[]>([]);
  const [selectedAdmins, setSelectedAdmins] = useState<number[]>([]);
  const [selectedExamen, setSelectedExamen] = useState<number | null>(null);
  const [selectedSalle, setSelectedSalle] = useState<number | null>(null);
  const [Adminlist, setAdminlist] = useState<any[]>([]);
  const [EnseignantList, setEnseignantList] = useState<any[]>([]);
  const [SalleList, setSalleList] = useState<any[]>([]);
  const [ExamenList, setExamenList] = useState<any[]>([]);

  const jwtToken = localStorage.getItem('token');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const examenResponse = await axios.get('http://localhost:8080/login/admin/getAllExamens', {
          headers: { Authorization: `Bearer ${jwtToken}` },
        });
        if (examenResponse.data.statusCode === 'OK') {
          setExamenList(examenResponse.data.body);
        } else {
          console.error('GET request error:', examenResponse.data.body);
        }

        const userResponse = await axios.get('http://localhost:8080/login/admin/getAllUsers', {

          headers: { Authorization: `Bearer ${jwtToken}` },
          
        });
        if (userResponse.data.statusCode === 'OK') {
          const UserList = userResponse.data.body;
          const admins = UserList.filter(user => user.type === 'ADMIN');
          setAdminlist(admins);
        } else {
          console.error('GET request error:', userResponse.data.body);
        }
      } catch (error) {
        console.error('GET request error:', error);
      }
    };

    fetchData();
  }, [jwtToken]);

  const navigate = useNavigate();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      const response = await axios.post(
        'http://localhost:8080/login/admin/affecterSalle',
        {
          examen_id: selectedExamen,
          salle_id: selectedSalle,
          enseignant_ids: selectedEnseignants,
          controll_absence_id: selectedAdmins[0],
        },
        { headers: { Authorization: `Bearer ${jwtToken}` } }
      );
      if (response.status === 200) {
        alert('Element added');
        window.location.reload();
      } else {
        console.error('Post request error:', response.data.body);
      }
    } catch (error) {
      console.error('Post request error:', error);
    }
  };

  const formatDateToDatabase = (date: Date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    const milliseconds = String(date.getMilliseconds()).padStart(3, '0') + '000';

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}.${milliseconds}`;
  };

  useEffect(() => {
    if (date) {
      const fetchSalleAndEnseignants = async () => {
        try {
          const salleResponse = await axios.get(`http://localhost:8080/login/admin/getEmptySalles/${date}`, {
            headers: { Authorization: `Bearer ${jwtToken}` },
          });
          if (salleResponse.data.statusCode === 'OK') {
            setSalleList(salleResponse.data.body);
          } else {
            console.error('GET request error:', salleResponse.data.body);
          }

          const enseignantResponse = await axios.get(`http://localhost:8080/login/admin/getFreeEnseignants/${date}`, {
            headers: { Authorization: `Bearer ${jwtToken}` },
          });
          if (enseignantResponse.data.statusCode === 'OK') {
            setEnseignantList(enseignantResponse.data.body);
          } else {
            console.error('GET request error:', enseignantResponse.data.body);
          }
        } catch (error) {
          console.error('GET request error:', error);
        }
      };

      fetchSalleAndEnseignants();
    }
  }, [date, jwtToken]);

  const handleExamenSelectionChange = (selectedIds: string[]) => {
    const selectedId = parseInt(selectedIds[0], 10);
    setSelectedExamen(selectedId);

    const selectedExam = ExamenList.find(exam => exam.id === selectedId);
    if (selectedExam) {
      const isoDate = new Date(selectedExam.date);
      const formattedDate = formatDateToDatabase(isoDate);
      setDate(formattedDate.replace(/ /g, '%20'));
    }
  };

  return (
    <DefaultLayout>
      <Breadcrumb pageName="Form User" />
      <div className="flex flex-col gap-9">
        <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
          <div className="border-b border-stroke py-4 px-6.5 dark:border-strokedark">
            <h3 className="font-medium text-black dark:text-white">Affecter</h3>
          </div>
          <form onSubmit={handleSubmit}>
            <div className="flex flex-col gap-5.5 p-6.5">
              <div>
                <label className="mb-3 block text-black dark:text-white">Examen</label>
                <MultiSelectModule
                  id="examenDropdown"
                  numberOfSelections={1}
                  enseignants={ExamenList}
                  onSelectionChange={handleExamenSelectionChange}
                />
              </div>
              <div>
                <label className="mb-3 block text-black dark:text-white">Salle</label>
                <MultiSelectModule
                  id="salleDropdown"
                  numberOfSelections={1}
                  enseignants={SalleList}
                  onSelectionChange={(ids) => setSelectedSalle(parseInt(ids[0], 10))}
                />
              </div>
              <div>
                <label className="mb-3 block text-black dark:text-white">Surveillant</label>
                <MultiSelect
                  id="enseignantDropdown"
                  numberOfSelections={5}
                  enseignants={EnseignantList}
                  onSelectionChange={(ids) => setSelectedEnseignants(ids.map(id => parseInt(id, 10)))}
                />
              </div>
              <div>
                <label className="mb-3 block text-black dark:text-white">Controll d'absence</label>
                <MultiSelect
                  id="adminDropdown"
                  numberOfSelections={1}
                  enseignants={Adminlist}
                  onSelectionChange={(ids) => setSelectedAdmins(ids.map(id => parseInt(id, 10)))}
                />
              </div>
            </div>
            <div className="flex justify-end py-4 px-6.5">
              <button
                type="submit"
                className="px-6 py-3 rounded-lg bg-primary text-white font-medium hover:bg-opacity-90 focus:outline-none focus:bg-opacity-90"
              >
                Submit
              </button>
            </div>
          </form>
        </div>
      </div>
    </DefaultLayout>
  );
};

export default Exam_salle_surv;
