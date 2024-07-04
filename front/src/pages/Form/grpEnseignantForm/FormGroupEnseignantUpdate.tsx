import React, { useState,useEffect, ChangeEvent } from 'react';
import DefaultLayout from '../../../layout/DefaultLayout';
import Breadcrumb from '../../../components/Breadcrumbs/Breadcrumb';
import { useNavigate } from 'react-router-dom'; // Import useNavigate hook
import axios from 'axios'; // Import axios for HTTP requests
import MultiSelect from '../MultiSelect';


const FormElementUpdate = () => {
  const [nom, setNom] = useState('');
  const [selectedEnseignants, setSelectedEnseignants] = useState<string[]>([]);
  const [userlist, setUserlist] = useState<any[]>([]);
  const jwtToken = localStorage.getItem('token');

  useEffect(() => {
    axios.get('http://localhost:8080/login/admin/getAllEnseignants', {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    }).then(response => {
      if (response.data.statusCode === "OK"){
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

  }, []);
  useEffect(() => {
    // Retrieve userToUpdate from local storage
    const grpToUpdateString = localStorage.getItem('GrpToUpdate');
    console.log(grpToUpdateString);
    // If userToUpdate exists in local storage, parse it into an object and set the state with its values
    if (grpToUpdateString) {
      const GrpToUpdate = JSON.parse(grpToUpdateString);
      setNom(GrpToUpdate.element.nom || '');
      console.log(nom);
    
    }
  }, []);
  const navigate = useNavigate(); // Access to history object

  const handleNomChange = (event: ChangeEvent<HTMLInputElement>) => {
    setNom(event.target.value);
  };




  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
  
    // Log the selectedEnseignants to check its value
    console.log("Selected enseignants:", selectedEnseignants);
  
    if (!selectedEnseignants || selectedEnseignants.length === 0) {
      // Handle the case where selectedEnseignants is null or empty
      console.error('No enseignants selected');
      alert('Please select at least one enseignant.');
      return;
    }
  
    const jwtToken = localStorage.getItem('token');
    const elementToUpdateString = localStorage.getItem('GrpToUpdate');
  
    if (!elementToUpdateString) {
      console.error('element to update not found in local storage');
      return;
    }
  
    const elementToUpdate = JSON.parse(elementToUpdateString);
    const Id = elementToUpdate.element.id;
  const ids = selectedEnseignants
    axios.put(
      `http://localhost:8080/login/admin/updateGrpEnseignant/${Id}`,  
      {ids,nom},

      {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    )
    .then(response => {
      if (response.data.statusCode === "OK") {
        console.log(response);
        alert("Group updated successfully");
        navigate("/forms/form-GroupEnseignantAction")
      } else {
        console.error('Update error:', response.data.body);
        throw new Error(response.data.body);
      }
    })
    .catch(error => {
      console.error('Update error:', error);
      alert('An error occurred while updating the group: ' + error.message);
    });
  };
  
  
  

  return (
    <DefaultLayout>
      <Breadcrumb pageName="Form Elements" />
        <div className="flex flex-col gap-9">
          <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
            <div className="border-b border-stroke py-4 px-6.5 dark:border-strokedark">
              <h3 className="font-medium text-black dark:text-white">
                Update a Group
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
                    Enseigants 
                  </label>
                  <MultiSelect
                    id="enseignantDropdown"
                    numberOfSelections={10}
                    enseignants={userlist}
                    onSelectionChange={setSelectedEnseignants} // Pass callback to update selected enseignants
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
