import { useEffect, useState } from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';

import Loader from './common/Loader';
import PageTitle from './components/PageTitle';
import SignIn from './pages/Authentication/SignIn';
import SignUp from './pages/Authentication/SignUp';
import Calendar from './pages/Calendar';
import Chart from './pages/Chart';
import ECommerce from './pages/Dashboard/ECommerce';
import FormUser from './pages/Form/FormUser';
import FormUpdate from './pages/Form/FormUpdate';
import Profile from './pages/Profile';
import Settings from './pages/Settings';
import Tables from './pages/Tables';
import Alerts from './pages/UiElements/Alerts';
import Buttons from './pages/UiElements/Buttons';
import FormAction from './pages/Form/FormAction';
import FormElements from './pages/Form/elementForm/FormElements';
import FormElementAction from './pages/Form/elementForm/FormElementAction';
import FormElementUpdate from './pages/Form/elementForm/FormElementUpdate';
import FormGrpEnseignants from './pages/Form/grpEnseignantForm/FormGrpEnseignants';
import FormGrpEnseignantAction from './pages/Form/grpEnseignantForm/FormGroupEnseignantAction';
import FormGroupEnseignantUpdate from './pages/Form/grpEnseignantForm/FormGroupEnseignantUpdate';
import FormModule from './pages/Form/moduleForm/FormModule';
import FormModuleAction from './pages/Form/moduleForm/FormModuleAction';
import FormModuleUpdate from './pages/Form/moduleForm/FormModuleUpdate';
import FormFiliere from './pages/Form/filiereForm/FormFiliere';
import FormFiliereAction from './pages/Form/filiereForm/FormFiliereAction';
import FormFiliereUpdate from './pages/Form/filiereForm/FormFiliereUpdate';
import FormDepartement from './pages/Form/departementForm/FormDepatement';
import FormDepartementAction from './pages/Form/departementForm/FormDepartementAction';
import FormDepatementUpdate from './pages/Form/departementForm/FormDepartementUpdate';
import FormSalle from './pages/Form/salleForm/FormSalle';
import FormSalleUpdate from './pages/Form/salleForm/FormSalleUpdate';
import FormSalleAction from './pages/Form/salleForm/FormSalleAction';
import FormExamen from './pages/Form/examenForm/FormExamen';
import FormExamenAction from './pages/Form/examenForm/FormExamenAction';
import Exam_salle_surv from './pages/Form/affectationExam/exam_salle_surv';
import FormExamenUpdate from './pages/Form/examenForm/FormExamenUpdate';
import Exam_salle_survAction from './pages/Form/affectationExam/Exam_salle_survAction';
import ElementAffectation from './pages/Form/elementForm/ElementAffectation';
import FinishExamen from './pages/Form/examenForm/FinishExamen';

function App() {
  const [loading, setLoading] = useState<boolean>(true);
  const { pathname } = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);

  useEffect(() => {
    setTimeout(() => setLoading(false), 1000);
  }, []);

  return loading ? (
    <Loader />
  ) : (
    <>
    
      <Routes>
      <Route
    index
    
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <SignIn />
      </>
    }
  />
      <Route
    index
    path="/index"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <SignIn />
      </>
    }
  />
  <Route
    path="/dashbord"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <ECommerce />
      </>
    }
  />
  <Route
    path="/Update-form"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormUpdate />
      </>
    }
  />
  <Route
    path="/calendar"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <Calendar />
      </>
    }
  />
  <Route
    path="/profile"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <Profile />
      </>
    }
  />
  <Route
    path="/forms/form-user"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormUser />
      </>
    }
  />
  <Route
    path="/forms/form-Action"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormAction />
      </>
    }
  />
  <Route
    path="/forms/form-elements"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormElements />
      </>
    }
  />
  <Route
    path="/forms/form-ElementAction"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormElementAction />
      </>
    }
  />
  <Route
    path="/forms/form-ElementUpdate"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormElementUpdate />
      </>
    }
  />
  <Route
    path="/forms/form-ElementAffectation"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <ElementAffectation />
      </>
    }
  />
  <Route
    path="/forms/form-GroupEnseignant"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormGrpEnseignants />
      </>
    }
  />
  <Route
    path="/forms/form-GroupEnseignantAction"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormGrpEnseignantAction />
      </>
    }
  />
  <Route
    path="/forms/form-GroupEnseignantUpdate"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormGroupEnseignantUpdate />
      </>
    }
  />
  <Route
    path="/forms/form-Module"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormModule />
      </>
    }
  />
  <Route
    path="/forms/form-ModuleAction"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormModuleAction />
      </>
    }
  />
  <Route
    path="/forms/form-ModuleUpdate"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormModuleUpdate />
      </>
    }
  />
  <Route
    path="/forms/form-Filiere"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormFiliere />
      </>
    }
  />
  <Route
    path="/forms/form-FiliereAction"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormFiliereAction />
      </>
    }
  />
  <Route
    path="/forms/form-FiliereUpdate"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormFiliereUpdate />
      </>
    }
  />
  <Route
    path="/forms/form-Departement"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormDepartement />
      </>
    }
  />
  <Route
    path="/forms/form-DepartementAction"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormDepartementAction />
      </>
    }
  />
  <Route
    path="/forms/form-DepartementUpdate"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormDepatementUpdate />
      </>
    }
  />
  <Route
    path="/forms/form-Salle"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormSalle />
      </>
    }
  />
  <Route
    path="/forms/form-SalleUpdate"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormSalleUpdate />
      </>
    }
  />
  <Route
    path="/forms/form-SalleAction"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormSalleAction />
      </>
    }
  />
  <Route
    path="/forms/form-Examen"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormExamen />
      </>
    }
  />
  <Route
    path="/forms/form-ExamenAction"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormExamenAction />
      </>
    }
  />
  <Route
    path="/forms/form-ExamenUpdate"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FormExamenUpdate />
      </>
    }
  />
  <Route
    path="/forms/form-affectationExam"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <Exam_salle_surv />
      </>
    }
  />
  <Route
    path="/forms/form-affectationExamAction"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <Exam_salle_survAction />
      </>
    }
  />
  <Route
    path="/forms/form-ExamenFinished"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <FinishExamen />
      </>
    }
  />
  <Route
    path="/tables"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <Tables />
      </>
    }
  />
  <Route
    path="/settings"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <Settings />
      </>
    }
  />
  <Route
    path="/chart"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <Chart />
      </>
    }
  />
  <Route
    path="/ui/alerts"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <Alerts />
      </>
    }
  />
  <Route
    path="/ui/buttons"
    element={
      <>
        <PageTitle title="Gestion des Exams | Mouad&Salah - Project" />
        <Buttons />
      </>
    }
  />
      </Routes>
    </>
  );
}

export default App;
