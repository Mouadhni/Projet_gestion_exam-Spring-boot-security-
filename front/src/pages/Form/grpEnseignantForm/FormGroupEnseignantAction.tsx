import { Link } from 'react-router-dom';
import Breadcrumb from '../../../components/Breadcrumbs/Breadcrumb';
import DefaultLayout from '../../../layout/DefaultLayout';
import { useEffect,useState } from 'react';
import axios from 'axios';
import TableGrpEnseignant from '../../../components/Tables/TableGrpEnseignant';
const FormGroupEnseignantAction = () => {
    const [grplist, setGrplist] = useState<any[]>([]);
  const jwtToken = localStorage.getItem('token');


  useEffect(() => {

    // Define your Axios GET request inside the useEffect hook
    axios.get('http://localhost:8080/login/admin/getAllGrpEnseignants', {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    }).then(response => {
        console.log("sdfghjk");
        console.log(response.status + "-------" + response.data.statusCode);
        if (response.data.statusCode === "OK") {
          console.log("ggg");
          // Handle successful response
          const GrpList = response.data.body; // Array of Enseignant objects
          console.log(GrpList);
          // Update the state with the fetched data
          setGrplist(GrpList);
        } else {
          // Handle error
          console.error('GET request error:', response.data.body);
          throw new Error(response.data.body);
        }
      })
      .catch(error => {
        // Display error message or perform other actions as needed
        console.error('GET request error:', error);
        alert(error.message +"mok"); // Example : Display error message in an alert
      });
  }, []); // The empty array as the second argument means this effect runs once after the first render
 
  
  
  return (
    <DefaultLayout>
      <Breadcrumb pageName="Form Layout" />
          <TableGrpEnseignant grpList={grplist}  title="User List" />
    </DefaultLayout>
  );
};

export default FormGroupEnseignantAction;
