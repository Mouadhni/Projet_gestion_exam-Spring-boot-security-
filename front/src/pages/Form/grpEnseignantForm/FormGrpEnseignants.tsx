import React, { useState,useEffect, ChangeEvent } from 'react';
import DefaultLayout from '../../../layout/DefaultLayout';
import Breadcrumb from '../../../components/Breadcrumbs/Breadcrumb';
import { useNavigate } from 'react-router-dom'; // Import useNavigate hook
import axios from 'axios'; // Import axios for HTTP requests
import MultiSelect from '../MultiSelect';

const FormGrpEnseignants = () => {
  const [nom, setNom] = useState('');
  const [userlist, setUserlist] = useState<any[]>([]);
  const [selectedEnseignants, setSelectedEnseignants] = useState<string[]>([]);

  const jwtToken = localStorage.getItem('token');

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

   
  }, []);

  const navigate = useNavigate(); // Access to history object

  const handleNomChange = (event: ChangeEvent<HTMLInputElement>) => {
    setNom(event.target.value);
  };

 
  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();



   const ids =selectedEnseignants;
   console.log(ids);

  
    axios.post(
      'http://localhost:8080/login/admin/createGrpEnseignant',
      { nom, ids },
      {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    )
      .then(response => {
        console.log("sdfghjk");
        console.log(response.status + "-------" + response.data.statusCode);
        if (response.data.statusCode === "OK") {
            
          alert("the group has been added ")

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
      <Breadcrumb pageName="Form User" />
        <div className="flex flex-col gap-9">
          <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
            <div className="border-b border-stroke py-4 px-6.5 dark:border-strokedark">
              <h3 className="font-medium text-black dark:text-white">
                Create a group
              </h3>
            </div>
            <form onSubmit={handleSubmit}>
              <div className="flex flex-col gap-5.5 p-6.5">
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Name of the group 
                  </label>
                  <input
                    type="text"
                    placeholder="Nom "
                    value={nom}
                    onChange={handleNomChange}
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  />
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Enseigant 
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

export default FormGrpEnseignants;
