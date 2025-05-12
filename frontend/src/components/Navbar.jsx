import SearchBox from "./navbaritems/SearchBox";
import UserDropDown from "./navbaritems/UserDropDown";

const Navbar = () => {
    return ( 
        <nav 
        className="bg-white shadow-md py-3 px-4 flex items-center">
            <a href="#" className="flex-shrink-0 h-8 w-auto">Lama App</a>
            <SearchBox/>
            <UserDropDown userName={'Ousama'} UserImage={"../assets/Screenshot from 2025-04-23 13-56-31.png"}/>
            
        </nav>
     );
}
 
export default Navbar;