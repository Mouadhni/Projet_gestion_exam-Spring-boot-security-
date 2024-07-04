import React, { useState, useEffect, ChangeEvent } from 'react';
import DefaultLayout from '../../../layout/DefaultLayout';
import Breadcrumb from '../../../components/Breadcrumbs/Breadcrumb';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import MultiSelect from '../MultiSelect';
import MultiSelectModule from '../MultiSelectModule';

const FormExamen = () => {
  const [nom, setNom] = useState('');
  const [semestre, setSemestre] = useState('PREMIER_Semestre');
  const [sesion, setSession] = useState('NORMALE');
  const [type, setType] = useState('DS');
  const [date, setDate] = useState('');
  const [heur, setHeure] = useState('8.30');
  const [dure_reel, setDurerReel] = useState('');
  const [dure_prevu, setDurerPrevue] = useState('');
  const [anneUniversitaire, setAnneeUniversitaire] = useState('');
  const [selectedEnseignants, setSelectedEnseignants] = useState<string[]>([]);
  const [selectedModules, setSelectedModules] = useState<string[]>([]);
  const [userlist, setUserlist] = useState<any[]>([]);
  const [moduleList, setModuleList] = useState<any[]>([]);
  const [epreuve, setEpreuveBase64] = useState<string | null>(null);

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
        console.error('GET request error:' , response.data.body);
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
  const arrayBufferToBase64 = (buffer: ArrayBuffer) => {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return btoa(binary);
  };
  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files[0]) {
      const file = event.target.files[0];
      const reader = new FileReader();
  
      // Callback function when file is loaded
      reader.onload = () => {
        // Convert array buffer to Base64 string
        const arrayBuffer = reader.result as ArrayBuffer;
        const base64String = arrayBufferToBase64(arrayBuffer);
  
        // Set the Base64 string in the state
        setEpreuveBase64(base64String);
      };
  
      // Start reading the file as ArrayBuffer
      reader.readAsArrayBuffer(file);
    }
  };
  const navigate = useNavigate();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
  
    const coordonnateur_id = selectedEnseignants.length > 0 ? selectedEnseignants[0] : '';
    const module_id = selectedModules.length > 0 ? selectedModules[0] : '';
  console.log(" testttt:  "+nom+" "+coordonnateur_id+" "+ module_id+" "+ semestre+" "+sesion+" "+ type+" "+ date+" "+ heur+" "+ dure_reel+" "+ dure_prevu+" "+anneUniversitaire+"---------"+epreuve )
    axios.post(
      'http://localhost:8080/login/admin/createExamen',
      { nom, coordonnateur_id, module_id, semestre, sesion, type, date, heur, dure_reel, dure_prevu, anneUniversitaire,epreuve },
      {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
      }
    ).then(response => {
      console.log(response)
      if (response.status == 200) {
        alert("Element added")
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
                Add an Exam
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
                    onChange={(e) => setNom(e.target.value)}
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  />
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Semestre 
                  </label>
                  <select value={semestre} onChange={(e) => setSemestre(e.target.value)}
                          className="h-full w-full appearance-none bg-transparent p-1 px-2 outline-none"
                  >
                    <option value="PREMIER_Semestre">1ER Semestre</option>
                    <option value="DEUXIEME_Semestre">2EME Semestre</option>
                  </select>
                </div>
                
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Session 
                  </label>
                  <select value={sesion} onChange={(e) => setSession(e.target.value)}
                          className="h-full w-full appearance-none bg-transparent p-1 px-2 outline-none"
                  >
                    
                    <option value="NORMALE">NORMALE</option>
                    <option value="RATRRAPAGE">RATRRAPAGE</option>

                  </select>
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Type 
                  </label>
                  <select value={type} onChange={(e) => setType(e.target.value)}
                  className="h-full w-full appearance-none bg-transparent p-1 px-2 outline-none">
                     <option value="DS">DS</option>
                    <option value="EXAMEN">EXAMEN Finale</option>
                  </select>
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Date 
                  </label>
                  <input
                    type="date"
                    value={date}
                    onChange={(e) => setDate(e.target.value)}
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  />
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Heure 
                  </label>
                  <select value={heur} onChange={(e) => setHeure(e.target.value)}
                  className="h-full w-full appearance-none bg-transparent p-1 px-2 outline-none">
                    <option value="8.30">8:30-10:30</option>
                    <option value="10.30">10:30-12:30</option>
                    <option value="2.30">2:30-4:30</option>
                    <option value="4.30">4:30-6:30</option>
                  </select>
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Durée Réelle 
                  </label>
                  <input
                    type="number"
                    value={dure_reel}
                    onChange={(e) => setDurerReel(e.target.value)}
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  />
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Durée Prévue 
                  </label>
                  <input
                    type="number"
                    value={dure_prevu}
                    onChange={(e) => setDurerPrevue(e.target.value)}
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  />
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Année Universitaire
                  </label>
                  <input
                    type="text"
                    value={anneUniversitaire}
                    onChange={(e) => setAnneeUniversitaire(e.target.value)}
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent py-3 px-5 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  />
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Coordonnateur 
                  </label>
                  <MultiSelect
                    id="enseignantDropdown"
                    numberOfSelections={1}
                    enseignants={userlist}
                    onSelectionChange={setSelectedEnseignants} // Pass callback to update selected modules
                  />
                </div>
                <div>
                  <label className="mb-3 block text-black dark:text-white">
                    Module 
                  </label>
                  <MultiSelectModule
                    id="enseignantDropdown"
                    numberOfSelections={1}
                    enseignants={moduleList}
                    onSelectionChange={setSelectedModules} // Pass callback to update selected modules
                  />
                </div>
                <div className="col-span-5 xl:col-span-2">
                <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
                  <div className="border-b border-stroke py-4 px-7 dark:border-strokedark">
                    <h3 className="font-medium text-black dark:text-white">
                      l'epreuve
                    </h3>
                  </div>
                  <div className="p-7">
                    <div className="mb-4 flex items-center gap-3">
                      <div className="h-14 w-14 rounded-full">
                      </div>
                      <div>
                        <span className="mb-1.5 text-black dark:text-white">
                          Entrer l epruve de cette exam 
                        </span>
                      </div>
                    </div>

                    <div
                      id="FileUpload"
                      className="relative mb-5.5 block w-full cursor-pointer appearance-none rounded border border-dashed border-primary bg-gray py-4 px-4 dark:bg-meta-4 sm:py-7.5"
                    >
                      <input
                        type="file"
                        onChange={handleFileChange}
                        accept="image/*,application/pdf"
                        className="absolute inset-0 z-50 m-0 h-full w-full cursor-pointer p-0 opacity-0 outline-none"
                      />
                      <div className="flex flex-col items-center justify-center space-y-3">
                        <span className="flex h-10 w-10 items-center justify-center rounded-full border border-stroke bg-white dark:border-strokedark dark:bg-boxdark">
                          <svg
                            width="16"
                            height="16"
                            viewBox="0 0 16 16"
                            fill="none"
                            xmlns="http://www.w3.org/2000/svg"
                          >
                            <path
                              fillRule="evenodd"
                              clipRule="evenodd"
                              d="M1.99967 9.33337C2.36786 9.33337 2.66634 9.63185 2.66634 10V12.6667C2.66634 12.8435 2.73658 13.0131 2.8616 13.1381C2.98663 13.2631 3.1562 13.3334 3.33301 13.3334H12.6663C12.8431 13.3334 13.0127 13.2631 13.1377 13.1381C13.2628 13.0131 13.333 12.8435 13.333 12.6667V10C13.333 9.63185 13.6315 9.33337 13.9997 9.33337C14.3679 9.33337 14.6663 9.63185 14.6663 10V12.6667C14.6663 13.1971 14.4556 13.7058 14.0806 14.0809C13.7055 14.456 13.1968 14.6667 12.6663 14.6667H3.33301C2.80257 14.6667 2.29387 14.456 1.91879 14.0809C1.54372 13.7058 1.33301 13.1971 1.33301 12.6667V10C1.33301 9.63185 1.63148 9.33337 1.99967 9.33337Z"
                              fill="#3C50E0"
                            />
                            <path
                              fillRule="evenodd"
                              clipRule="evenodd"
                              d="M7.5286 1.52864C7.78894 1.26829 8.21106 1.26829 8.4714 1.52864L11.8047 4.86197C12.0651 5.12232 12.0651 5.54443 11.8047 5.80478C11.5444 6.06513 11.1223 6.06513 10.8619 5.80478L8 2.94285L5.13807 5.80478C4.87772 6.06513 4.45561 6.06513 4.19526 5.80478C3.93491 5.54443 3.93491 5.12232 4.19526 4.86197L7.5286 1.52864Z"
                              fill="#3C50E0"
                            />
                            <path
                              fillRule="evenodd"
                              clipRule="evenodd"
                              d="M7.99967 1.33337C8.36786 1.33337 8.66634 1.63185 8.66634 2.00004V10C8.66634 10.3682 8.36786 10.6667 7.99967 10.6667C7.63148 10.6667 7.33301 10.3682 7.33301 10V2.00004C7.33301 1.63185 7.63148 1.33337 7.99967 1.33337Z"
                              fill="#3C50E0"
                            />
                          </svg>
                        </span>
                        <p>
                          <span className="text-primary">Click to upload</span> or
                          drag and drop
                        </p>
                        <p className="mt-1.5">SVG, PNG, JPG, GIF, or PDF</p>
                        <p>(max, 800 X 800px)</p>
                      </div>
                    </div>

                   
                  </div>
                </div>
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

export default FormExamen;
