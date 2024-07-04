import { Link } from 'react-router-dom';
import Breadcrumb from '../../components/Breadcrumbs/Breadcrumb';
import DefaultLayout from '../../layout/DefaultLayout';
import { useEffect,useState } from 'react';
import axios from 'axios';
import TableThree from '../../components/Tables/TableThree';
const FormAction = () => {
    const [userlist, setUserlist] = useState<any[]>([]);
  const jwtToken = localStorage.getItem('token');


  useEffect(() => {

    // Define your Axios GET request inside the useEffect hook
    axios.get('http://localhost:8080/login/admin/getAllUsers', {
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    }).then(response => {
        console.log("sdfghjk");
        console.log(response.status + "-------" + response.data.statusCode);
        if (response.data.statusCode === "OK") {
          console.log("ggg");
          // Handle successful response
          const UserList = response.data.body; // Array of Enseignant objects
          console.log(UserList);
          // Update the state with the fetched data
          setUserlist(UserList);
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
  const admins = userlist.filter(user => user.type === 'ADMIN');
  const enseignants = userlist.filter(user => user.type === 'ENSEIGNANT');
  
  console.log('Admins:', admins);
  console.log('Enseignants:', enseignants);
  return (
    <DefaultLayout>
      <Breadcrumb pageName="Form Layout" />
          <TableThree userList={userlist}  title="User List"/>
    </DefaultLayout>
  );
};

export default FormAction;
