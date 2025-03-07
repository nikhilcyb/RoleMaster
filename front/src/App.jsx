// import React from 'react';
// import { BrowserRouter } from 'react-router-dom';
// // import AdminLayout from 'layouts/AdminLayout';

// import routes, { renderRoutes } from './routes';

// const App = () => {
//   return <BrowserRouter basename={import.meta.env.VITE_APP_BASE_NAME}>{renderRoutes(routes)}</BrowserRouter>;
// };

// export default App;


// import React from 'react';
// import { BrowserRouter, Routes, Route } from 'react-router-dom';
// import SignIn from 'views/ui-elements/basic/Basicsignin';
// import AdminLayout from 'layouts/AdminLayout';



//  const App = () => {
//   return (
//     <BrowserRouter basename={import.meta.env.VITE_APP_BASE_NAME}>
//       <Routes>
//         {/* Default route */}
//         <Route path="/" element={<SignIn/>} /> 
//             {/* <Route path="/" element={<AdminLayout/>} />  */}


//        </Routes>
//     </BrowserRouter>
//   );
//  };

// export default App;


import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import SignIn from 'views/ui-elements/basic/Basicsignin';
import AdminLayout from 'layouts/AdminLayout';
import Basicuser from 'views/ui-elements/basic/Basicuser';
import Basicorganization from 'views/ui-elements/basic/Basicorganization';
import Basicoumapping from 'views/ui-elements/basic/Basicoumapping';

const App = () => {
  return (
    <BrowserRouter basename={import.meta.env.VITE_APP_BASE_NAME}>
      <Routes>
        <Route path="/" element={<SignIn />} />
        <Route path="/dashboard/*" element={<AdminLayout />} /> {/* Fix: AdminLayout should be under "/dashboard" */}
        <Route path="/basic/user/*" element={<Basicuser />} />
        <Route path="/basic/organization/*"  element={<Basicorganization />} />
        <Route path="/basic/oumapping/*"  element={<Basicoumapping />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
