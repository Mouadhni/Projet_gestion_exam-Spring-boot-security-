import { Link } from 'react-router-dom';
import Breadcrumb from '../../../components/Breadcrumbs/Breadcrumb';
import DefaultLayout from '../../../layout/DefaultLayout';
import { useEffect,useState } from 'react';
import axios from 'axios';

import TableModule from '../../../components/Tables/TableModule';
const FormModuleAction = () => {
    const [elementlist, setElementlist] = useState<any[]>([]);
  const jwtToken = localStorage.getItem('token');


  useEffect(() => {

    // Define your Axios GET request inside the useEffect hook
    axios.get('http://localhost:8080/login/admin/getAllModules', {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    }).then(response => {
        console.log("sdfghjk");
        console.log(response.status + "-------" + response.data.statusCode);
        if (response.data.statusCode === "OK") {
          console.log("ggg");
          // Handle successful response
          const ElementList = response.data.body; // Array of Enseignant objects
          console.log(ElementList);
          // Update the state with the fetched data
          setElementlist(ElementList);
        } else {
          // Handle error
          console.error('GET request error:', response.data.body);
          throw new Error(response.data.body);
        }
      })
      .catch(error => {
        // Display error message or perform other actions as needed
        console.error('GET request error:', error);
        alert(error.message +"mok"); // Example: Display error message in an alert
      });
  }, []); // The empty array as the second argument means this effect runs once after the first render

  

  return (
    <DefaultLayout>
      <Breadcrumb pageName="Form Layout" />
          <TableModule elementlist={elementlist}  title="Element List"/>
    </DefaultLayout>
  );
};

export default FormModuleAction;
