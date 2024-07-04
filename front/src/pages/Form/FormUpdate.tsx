import React, { useState,useEffect, ChangeEvent } from 'react';
import DefaultLayout from '../../layout/DefaultLayout';
import Breadcrumb from '../../components/Breadcrumbs/Breadcrumb';
import { useNavigate } from 'react-router-dom'; // Import useNavigate hook
import axios from 'axios'; // Import axios for HTTP requests

const FormUpdate = () => {
  const [username, setUsername] = useState('');
  const [lastname, setlastname] = useState('');
  const [email, setemail] = useState('');
  const [type, setType] = useState('');
  useEffect(() => {
    // Retrieve userToUpdate from local storage
    const userToUpdateString = localStorage.getItem('userToUpdate');
    // If userToUpdate exists in local storage, parse it into an object and set the state with its values
    if (userToUpdateString) {
      const userToUpdate = JSON.parse(userToUpdateString);
      setUsername(userToUpdate.username || '');
      setlastname(userToUpdate.lastname || '');
      setemail(userToUpdate.email || '');
      setType(userToUpdate.type || '');
    }
  }, []);
  const navigate = useNavigate(); // Access to history object

  const handleUsernameChange = (event: ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value);
  };


  const handleLastNameChange = (event: ChangeEvent<HTMLInputElement>) => {
    setlastname(event.target.value);
  };

  const handleEmailChange = (event: ChangeEvent<HTMLInputElement>) => {
    setemail(event.target.value);
  };

  const handleTypeChange = (event: ChangeEvent<HTMLSelectElement>) => {
    setType(event.target.value);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const jwtToken = localStorage.getItem('token');
    // Retrieve user ID from userToUpdate object in local storage
    const userToUpdateString = localStorage.getItem('userToUpdate');
    if (!userToUpdateString) {
      console.error('User to update not found in local storage');
      return;
    }
    const userToUpdate = JSON.parse(userToUpdateString);
    const userId = userToUpdate.id;
  
    axios.put(
      `http://localhost:8080/login/admin/update/${userId}`, // Pass the user ID to the URL
      { username,email, type, lastname },
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
        if (response.data.statusCode == "OK") {
          console.log(response);
          
          if (type =="ADMIN") {
            alert("admin has been added");
            navigate("/forms/form-Action");

            // Handle successful login response
           } else if (type == "ENSEIGNANT") {
            // Handle successful login response
            alert("Enseignant has been added");
            navigate("/forms/form-Action");
  
          } else {
            throw new Error("Type invalid");
          }
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
                Add a User
              </h3>
            </div>
            <form onSubmit={handleSubmit}>
              <div className="flex flex-col gap-5.5 p-6.5">
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Username
                  </label>
                  <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={handleUsernameChange}
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  />
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Last name
                  </label>
                  <input
                    type="text"
                    placeholder="Last Name"
                    value={lastname}
                    onChange={handleLastNameChange}
                    className="w-full rounded-lg border-[1.5px] border-primary bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white"
                  />
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Email
                  </label>
                  <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={handleEmailChange}
                    className="w-full rounded-lg border-[1.5px] border-primary bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white"
                  />
                </div>
               
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Type
                  </label>
                  <select
                    value={type}
                    onChange={handleTypeChange}
                    className="w-full rounded-lg border-[1.5px] border-primary bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:bg-form-input dark:text-white"
                  >
                    <option value="ADMIN">Admin</option>
                    <option value="ENSEIGNANT">Enseignant</option>
                  </select>
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

export default FormUpdate;
