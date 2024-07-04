import React, { useState, useEffect, ChangeEvent } from 'react';
import DefaultLayout from '../../../layout/DefaultLayout';
import Breadcrumb from '../../../components/Breadcrumbs/Breadcrumb';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import MultiSelect from '../MultiSelect';
import MultiSelectModule from '../MultiSelectModule';

const FormElements = () => {
  const [nom, setNom] = useState('');
  const [selectedEnseignants, setSelectedEnseignants] = useState<string[]>([]);
  const [selectedModules, setSelectedModules] = useState<string[]>([]);
  const [userlist, setUserlist] = useState<any[]>([]);
  const [moduleList, setModuleList] = useState<any[]>([]);
  const jwtToken = localStorage.getItem('token');
  console.log("Selected Enseignants:", selectedEnseignants);
  console.log("Selected Modules:", selectedModules);
  useEffect(() => {
    axios.get('http://localhost:8080/login/admin/getAllEnseignants', {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    }).then(response => {
      if (response.data.statusCode === "OK") {
        const UserList = response.data.body;
        setUserlist(UserList);
      } else {
        console.error('GET request error:', response.data.body);
        throw new Error(response.data.body);
      }
    }).catch(error => {
      console.error('GET request error:', error);
      alert(error.message);
    });

    axios.get('http://localhost:8080/login/admin/getAllModules', {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    }).then(response => {
      if (response.data.statusCode === "OK") {
        const ModuleList = response.data.body;
        setModuleList(ModuleList);
      } else {
        console.error('GET request error:', response.data.body);
        throw new Error(response.data.body);
      }
    }).catch(error => {
      console.error('GET request error:', error);
      alert(error.message);
    });
  }, []);

  const navigate = useNavigate();

  const handlenomChange = (event: ChangeEvent<HTMLInputElement>) => {
    setNom(event.target.value);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const jwtToken = localStorage.getItem('token');
  
    // Extract the first element from the selectedEnseignants and selectedModules arrays
    const enseignant_id = selectedEnseignants.length > 0 ? selectedEnseignants[0] : '';
    const module_id = selectedModules.length > 0 ? selectedModules[0] : '';
  
    axios.post(
      'http://localhost:8080/login/admin/createElement',
      { nom, enseignant_id, module_id },
      {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    ).then(response => {
      console.log(response)
      if (response.status == 200) {
        alert("element added ")
        navigate("/dashboard")
      } else {
        console.error('Login error:', response.data.body);
        throw new Error(response.data.body);
      }
    }).catch(error => {
      console.error('Login error:', error);
      alert(error.message);
    });
  };

  return (
    <DefaultLayout>
      <Breadcrumb pageName="Form User" />
        <div className="flex flex-col gap-9">
          <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
            <div className="border-b border-stroke py-4 px-6.5 dark:border-strokedark">
              <h3 className="font-medium text-black dark:text-white">
                Add a Element
              </h3>
            </div>
            <form onSubmit={handleSubmit}>
              <div className="flex flex-col gap-5.5 p-6.5">
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Nom 
                  </label>
                  <input
                    type="text"
                    placeholder="nom"
                    value={nom}
                    onChange={handlenomChange}
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  />
                </div>
                
               
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Coordinateur 
                  </label>
                  <MultiSelect
                    id="enseignantDropdown"
                    numberOfSelections={1}
                    enseignants={userlist}
                    onSelectionChange={setSelectedEnseignants} // Pass callback to update selected enseignants
                  />
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Module  
                  </label>
                  <MultiSelectModule  
                    id="moduleDropdown"
                    numberOfSelections={1}
                    enseignants={moduleList}
                    onSelectionChange={setSelectedModules} // Pass callback to update selected modules
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

export default FormElements;
