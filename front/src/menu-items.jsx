// const menuItems = {
//   items: [
//     {
//       id: 'navigation',
//       title: 'Navigation',
//       type: 'group',
//       icon: 'icon-navigation',
//       children: [
        
//         {
//           id: 'dashboard',
//           title: 'Dashboard',
//           type: 'item',
//           icon: 'feather icon-home',
//           url: '/app/dashboard/default',
//         },
//       ],
//     },
//     {
//       id: 'ui-element',
//       title: 'UI ELEMENT',
//       type: 'group',
//       icon: 'icon-ui',
//       children: [
//         {
//           id: 'component',
//           title: 'Component',
//           type: 'collapse',
//           icon: 'feather icon-box',
//           children: [
//             // {
//             //   id: 'signin',
//             //   title: 'Signin',
//             //   type: 'item',
//             //   url: '/basic/signin',
//             // },
//             {
//               id: 'user',
//               title: 'User',
//               type: 'item', // Changed to collapse to accommodate children
//               url: '/basic/user',
             
//             },
//             {
//               id: 'organization',
//               title: 'Organization',
//               type: 'item',
//               url: '/basic/organization',
//             },
//             {
//               id: 'oumapping',
//               title: 'Organization User Mapping',
//               type: 'item',
//               url: '/basic/oumapping',
//             },
//           ],
//         },
//       ],
//     },
//   ],
// };

// export default menuItems;

import Cookies from 'js-cookie';
import jwtDecode from 'jwt-decode';

// Function to retrieve userRoleId from token
const getUserRoleId = () => {
  const token = Cookies.get('authToken'); // Retrieve token from cookies

  if (token) {
    try {
      const decodedToken = jwtDecode(token); // Decode the JWT
      return decodedToken.userRoleId || null; // Extract userRoleId
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  }
  return null;
};

// Function to get the menu items dynamically based on userRoleId
const getMenuItems = () => {
  const userRoleId = getUserRoleId(); // Fetch role dynamically
  let dashboardChildren = [];

  // Define menu items based on the userRoleId
  if (userRoleId === 1) {
    dashboardChildren = [
      { id: 'user', title: 'User', type: 'item', url: '/basic/user' },
      { id: 'organization', title: 'Organization', type: 'item', url: '/basic/organization' },
      { id: 'oumapping', title: 'Organization User Mapping', type: 'item', url: '/basic/oumapping' }
    ];
  } else if (userRoleId === 2) {
    dashboardChildren = [
      { id: 'user', title: 'User', type: 'item', url: '/basic/user' }
    ];
  } else if (userRoleId === 3) {
    dashboardChildren = [
      { id: 'organization', title: 'Organization', type: 'item', url: '/basic/organization' }
    ];
  }
  else if (userRoleId === 11) {
    dashboardChildren = [
      { id: 'oumapping', title: 'Organization User Mapping', type: 'item', url: '/basic/oumapping' }
    ];
  }

  // Ensure menuItems structure is valid and always an array
  return {
    items: dashboardChildren.length > 0
      ? [{
          id: 'navigation',
          title: 'Navigation',
          type: 'group',
          icon: 'icon-navigation',
          children: [{
            id: 'dashboard',
            title: 'Dashboard',
            type: 'collapse',
            icon: 'feather icon-home',
            children: dashboardChildren,
          }],
        }]
      : [], // Empty array before login
  };
};

// Export menu items directly as a dynamic value
const menuItems = getMenuItems();

export default menuItems;
