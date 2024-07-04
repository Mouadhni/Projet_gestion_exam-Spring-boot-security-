import React, { useState,useEffect, ChangeEvent } from 'react';
import DefaultLayout from '../../../layout/DefaultLayout';
import Breadcrumb from '../../../components/Breadcrumbs/Breadcrumb';
import { useNavigate } from 'react-router-dom'; // Import useNavigate hook
import axios from 'axios'; // Import axios for HTTP requests
import MultiSelect from '../MultiSelect';
import MultiSelectModule from '../MultiSelectModule';


const FormElementUpdate = () => {
  const [nom, setNom] = useState('');
  const [filiere_id, seFiliere_id] = useState('');
  const [Id, setid] = useState('');


  const [description, setDescription] = useState('');
  const [selectedModules, setSelectedModules] = useState<string[]>([]);
  const [moduleList, setModuleList] = useState<any[]>([]);
  const jwtToken = localStorage.getItem('token');

  useEffect(() => {
   

    axios.get('http://localhost:8080/login/admin/getAllFilieres', {
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
  
  useEffect(() => {
    // Retrieve userToUpdate from local storage
    const elementToUpdateString = localStorage.getItem('moduleToUpdate');
    console.log(elementToUpdateString);
    // If userToUpdate exists in local storage, parse it into an object and set the state with its values
    if (elementToUpdateString) {
      const elemntToUpdate = JSON.parse(elementToUpdateString);
      setNom(elemntToUpdate.element.nom || '');
      setDescription(elemntToUpdate.element.description || '');
      setid(elemntToUpdate.element.id || '');
    const Id = elemntToUpdate.element.id;
    const filiere_id = selectedModules.length > 0 ? selectedModules[0] : '';
    seFiliere_id(filiere_id)
    console.log(Id+" "+" "+filiere_id)
    }
  }, []);
  const navigate = useNavigate(); // Access to history object

  const handleNomChange = (event: ChangeEvent<HTMLInputElement>) => {
    setNom(event.target.value);
  };
  const handleDescriptionChange = (event: ChangeEvent<HTMLInputElement>) => {
    setDescription(event.target.value);
  };




  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const jwtToken = localStorage.getItem('token');
    // Retrieve user ID from userToUpdate object in local storage
    
    
  
    axios.put(

      `http://localhost:8080/login/admin/updateModule/${Id}`,
      { nom,description, filiere_id },
      {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },

      }
    )
      .then(response => {
        console.log("sdfghjk");
        console.log(response);
        console.log(response.status + "-------" + response.data.statusCode);
        if (response.data.statusCode == "OK"){
          
          console.log(response);
          alert("Module got updated successfully")


        } else {
          // Handle login error
          console.error('Login error:', response.data.body);
          throw new Error(response.data.body);
        }
      })
      .catch(error => {
        // Display error message or perform other actions as needed
        console.error('Login error:', error);
        alert(error.message); // Example: Display error message in an alert
      });
  };
  
  
  

  return (
    <DefaultLayout>
      <Breadcrumb pageName="Form Elements" />
        <div className="flex flex-col gap-9">
          <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
            <div className="border-b border-stroke py-4 px-6.5 dark:border-strokedark">
              <h3 className="font-medium text-black dark:text-white">
                Update a Module 
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
                    placeholder="Nom"
                    value={nom}
                    onChange={handleNomChange}
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  />
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Description
                  </label>
                  <input
                    type="text"
                    placeholder="Nom"
                    value={description}
                    onChange={handleDescriptionChange}
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  />
                </div>
               
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Filiere   
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

export default FormElementUpdate;
