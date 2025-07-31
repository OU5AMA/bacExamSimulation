import { useEffect } from "react";
import { useRef } from "react";
import { useState } from "react";


const UserDropDown = ({userName, UserImage}) => {

    // eslint-disable-next-line react-hooks/rules-of-hooks
const [isOpen, setIsOpen] = useState(false);
const dropdownRef = useRef(null);


// Close dropDown when clicking outside
useEffect(() => {
    const handleClickOutside = (event) => {
        if (dropdownRef.current && !dropdownRef.current.contains(event.target)){
            setIsOpen(false);
        }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return ()=> {
        document.removeEventListener('mousedown', handleClickOutside);
    };
}, [])


    return ( 
        <div className="flex-shrink-0 ml-auto" 
        ref={dropdownRef}>
            <div className="relative inline-block text-left pl-2 ">
                <button 
                    type="button"
                    className="inline-flex.items-center.justify-center.w-full.rounded-md.px-4.py-2.bg-white.text-sm.font-medium.text-gray-700.hover:bg-gray-50.focus:ring-2.focus:ringoffset-2.focus:ring-indigo-500"
                    id="user-menu"
                    aria-expanded={isOpen}
                    aria-haspopup="true"
                    onClick={() => setIsOpen(!isOpen)}>

                    <span className="mr-2 flex">
                        <img 
                            src={UserImage} 
                            className="h-8 w-8 rounded-full" 
                            alt="" 
                            srcset="" 

                        />
                        {userName} 

                        <i className="fas fa-chevron-down text-gray-500 text-sm ml-1"></i>
                        <svg 
          className="w-4 h-4 text-gray-500" 
          xmlns="http://www.w3.org/2000/svg" 
          viewBox="0 0 20 20" 
          fill="currentColor"
        >
          <path 
            fillRule="evenodd" 
            d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" 
            clipRule="evenodd" 
          />
        </svg>


                    </span>
                </button>
            </div>

            {/* Menu, it opens when the state isOpen is true */}
            {isOpen && (
                <div 
                className="origin-top-right absolute right-0 mt-2 w-56 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none"
                role="menu"
                aria-orientation="vertical"
                aria-labelledby="user-menu"
                >
                    <div className="py-1" role="none">
                        <a href="#" role="menuItem" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Your Profile</a>
                        <a href="#" role="menuItem" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Sitting</a>
                        <a href="#" role="menuItem" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Sign Out</a>
                    </div>
                </div>
            )}
        </div>

     );
}
 
export default UserDropDown;