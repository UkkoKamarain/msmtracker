package tools.msm.b_tracking_list.web;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tools.msm.b_tracking_list.domain.MsmUser;
import tools.msm.b_tracking_list.domain.MsmUserRepository;

@Service
public class UDSImp implements UserDetailsService {
    private final MsmUserRepository uR;

    public UDSImp(MsmUserRepository ur) {
        this.uR = ur;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MsmUser currentUser = uR.findByUsername(username);
        UserDetails user = new User(username, currentUser.getPassword(),
                AuthorityUtils.createAuthorityList(currentUser.getRole()));
        return user;
    }}
